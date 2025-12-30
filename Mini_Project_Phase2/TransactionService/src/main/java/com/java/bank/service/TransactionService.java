package com.java.bank.service;

import com.java.bank.client.AccountClient;
import com.java.bank.client.NotificationClient;
import com.java.bank.dto.AccountDTO;
import com.java.bank.model.Transaction;
import com.java.bank.repo.TransactionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private NotificationClient notificationClient;

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private static final String ACCOUNT_CB = "accountServiceCB";
    private static final String NOTIFICATION_CB = "notificationServiceCB";

    // ==================== DEPOSIT ====================
    public Transaction deposit(String accountNumber, double amount) {
        String correlationId = UUID.randomUUID().toString();
        logger.info("CorrelationID {}: Starting deposit of {} to account {}", correlationId, amount, accountNumber);

        try {
            AccountDTO account = depositCB(accountNumber, amount); // CircuitBreaker wrapped
            Transaction txn = saveTransaction("DEPOSIT", amount, "SUCCESS", accountNumber, null, correlationId);

            try {
                sendNotificationCB("Deposit of " + amount + " successful in account " + accountNumber, correlationId);
            } catch (Exception ignored) {}

            return txn;
        } catch (Exception e) {
            return depositFallback(accountNumber, amount, e);
        }
    }

    @CircuitBreaker(name = ACCOUNT_CB, fallbackMethod = "depositFallback")
    private AccountDTO depositCB(String accountNumber, double amount) {
        return accountClient.updateBalance(accountNumber, amount);
    }

    public Transaction depositFallback(String accountNumber, double amount, Throwable t) {
        logger.error("Deposit fallback triggered for account {}: {}", accountNumber, t.getMessage());
        return saveTransaction("DEPOSIT", amount, "FAILED", accountNumber, null, UUID.randomUUID().toString());
    }

    // ==================== WITHDRAW ====================
    public Transaction withdraw(String accountNumber, double amount) {
        String correlationId = UUID.randomUUID().toString();
        logger.info("CorrelationID {}: Starting withdrawal of {} from account {}", correlationId, amount, accountNumber);

        try {
            AccountDTO account = getAccountCB(accountNumber);
            if (account == null || account.getBalance() < amount) {
                logger.error("CorrelationID {}: Withdrawal failed due to insufficient balance in account {}", correlationId, accountNumber);
                return saveTransaction("WITHDRAW", amount, "FAILED", accountNumber, null, correlationId);
            }

            updateBalanceCB(accountNumber, -amount);
            Transaction txn = saveTransaction("WITHDRAW", amount, "SUCCESS", accountNumber, null, correlationId);

            try {
                sendNotificationCB("Withdrawal of " + amount + " successful from account " + accountNumber, correlationId);
            } catch (Exception ignored) {}

            return txn;
        } catch (Exception e) {
            return withdrawFallback(accountNumber, amount, e);
        }
    }

    @CircuitBreaker(name = ACCOUNT_CB, fallbackMethod = "withdrawFallback")
    private AccountDTO getAccountCB(String accountNumber) {
        return accountClient.getAccount(accountNumber);
    }

    @CircuitBreaker(name = ACCOUNT_CB, fallbackMethod = "withdrawFallback")
    private AccountDTO updateBalanceCB(String accountNumber, double amount) {
        return accountClient.updateBalance(accountNumber, amount);
    }

    public Transaction withdrawFallback(String accountNumber, double amount, Throwable t) {
        logger.error("Withdrawal fallback triggered for account {}: {}", accountNumber, t.getMessage());
        return saveTransaction("WITHDRAW", amount, "FAILED", accountNumber, null, UUID.randomUUID().toString());
    }

    // ==================== TRANSFER ====================
    public Transaction transfer(String fromAccount, String toAccount, double amount) {
        String correlationId = UUID.randomUUID().toString();
        logger.info("CorrelationID {}: Starting transfer of {} from {} to {}", correlationId, amount, fromAccount, toAccount);

        try {
            AccountDTO source = updateBalanceCB(fromAccount, -amount);
            if (source == null) throw new RuntimeException("Debit failed");

            AccountDTO destination = updateBalanceCB(toAccount, amount);
            if (destination == null) {
                updateBalanceCB(fromAccount, amount); // rollback
                throw new RuntimeException("Credit failed");
            }

            Transaction txn = saveTransaction("TRANSFER", amount, "SUCCESS", fromAccount, toAccount, correlationId);

            try {
                sendNotificationCB("Transfer of " + amount + " from " + fromAccount + " to " + toAccount + " successful.", correlationId);
            } catch (Exception ignored) {}

            return txn;
        } catch (Exception e) {
            return transferFallback(fromAccount, toAccount, amount, e);
        }
    }

    @CircuitBreaker(name = ACCOUNT_CB, fallbackMethod = "transferFallback")
    public Transaction transferFallback(String fromAccount, String toAccount, double amount, Throwable t) {
        logger.error("Transfer fallback triggered from {} to {}: {}", fromAccount, toAccount, t.getMessage());
        return saveTransaction("TRANSFER", amount, "FAILED", fromAccount, toAccount, UUID.randomUUID().toString());
    }

    // ==================== NOTIFICATION ====================
    @CircuitBreaker(name = NOTIFICATION_CB, fallbackMethod = "notificationFallback")
    private void sendNotificationCB(String message, String correlationId) {
        Map<String, String> payload = new HashMap<>();
        payload.put("message", message);
        payload.put("correlationId", correlationId);
        notificationClient.sendNotification(payload);
    }

    private void notificationFallback(String message, String correlationId, Throwable t) {
        logger.error(
                "Notification fallback triggered: {} | CorrelationID: {} | Reason: {}",
                message, correlationId, t.getMessage()
        );
    }

    // ==================== GET TRANSACTIONS ====================
    public List<Transaction> getTransactions(String accountNumber) {
        return repository.findBySourceAccountOrDestinationAccount(accountNumber, accountNumber);
    }

    // ==================== SAVE TRANSACTION ====================
    private Transaction saveTransaction(String type, double amount, String status, String source, String destination, String correlationId){
        Transaction txn = new Transaction(
                "TXN-" + System.currentTimeMillis(),
                type,
                amount,
                new Date(),
                status,
                source,
                destination
        );

        repository.save(txn);
        logger.info("CorrelationID {}: Transaction of type {} saved with status {}", correlationId, type, status);
        return txn;
    }
}
