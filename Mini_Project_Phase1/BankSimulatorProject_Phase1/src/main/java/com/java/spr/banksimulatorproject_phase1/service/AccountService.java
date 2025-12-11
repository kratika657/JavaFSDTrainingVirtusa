package com.java.spr.banksimulatorproject_phase1.service;

import com.java.spr.banksimulatorproject_phase1.exception.AccountNotFoundException;
import com.java.spr.banksimulatorproject_phase1.exception.InsufficientBalanceException;
import com.java.spr.banksimulatorproject_phase1.exception.InvalidAmountException;
import com.java.spr.banksimulatorproject_phase1.model.Account;
import com.java.spr.banksimulatorproject_phase1.model.Transaction;
import com.java.spr.banksimulatorproject_phase1.repo.AccountRepository;
import com.java.spr.banksimulatorproject_phase1.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // ------------------ CREATE ACCOUNT ------------------
    public Account createAccount(String holderName) {
        logger.info("Creating account for '{}'", holderName);
        if (holderName == null || holderName.isEmpty()) {
            logger.warn("Failed to create account: Holder name is empty");
            throw new IllegalArgumentException("Holder name cannot be empty");
        }

        String accountNumber = generateAccountNumber(holderName);

        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setHolderName(holderName);
        account.setBalance(0.0);
        account.setStatus("ACTIVE");
        account.setCreatedAt(new Date());

        Account saved = accountRepository.save(account);
        logger.info("Account created successfully: accountNumber={}", accountNumber);
        return saved;
    }

    // ------------------ GET ACCOUNT ------------------
    public Account getAccount(String accountNumber) {
        logger.info("Fetching account '{}'", accountNumber);
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> {
                    logger.warn("Account not found: {}", accountNumber);
                    return new AccountNotFoundException("Account not found: " + accountNumber);
                });
        logger.info("Account fetched successfully: {}", accountNumber);
        return account;
    }

    // ------------------ DEPOSIT ------------------
    public Transaction deposit(String accountNumber, double amount) {
        logger.info("Depositing {} to account '{}'", amount, accountNumber);
        if (amount <= 0) {
            logger.warn("Invalid deposit amount: {}", amount);
            throw new InvalidAmountException("Deposit amount must be positive");
        }

        Account account = getAccount(accountNumber);
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        Transaction txn = new Transaction();
        txn.setTransactionId(generateTransactionId());
        txn.setType("DEPOSIT");
        txn.setAmount(amount);
        txn.setStatus("SUCCESS");
        txn.setTimestamp(new Date());
        txn.setSourceAccount(accountNumber);
        txn.setDestinationAccount(null);

        transactionRepository.save(txn);
        logger.info("Deposit successful for account '{}', amount {}", accountNumber, amount);
        return txn;
    }

    // ------------------ WITHDRAW ------------------
    public Transaction withdraw(String accountNumber, double amount) {
        logger.info("Withdrawing {} from account '{}'", amount, accountNumber);
        if (amount <= 0) {
            logger.warn("Invalid withdraw amount: {}", amount);
            throw new InvalidAmountException("Withdraw amount must be positive");
        }

        Account account = getAccount(accountNumber);
        if (account.getBalance() < amount) {
            logger.warn("Insufficient balance for account '{}': requested={}, available={}", accountNumber, amount, account.getBalance());
            throw new InsufficientBalanceException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        Transaction txn = new Transaction();
        txn.setTransactionId(generateTransactionId());
        txn.setType("WITHDRAW");
        txn.setAmount(amount);
        txn.setStatus("SUCCESS");
        txn.setTimestamp(new Date());
        txn.setSourceAccount(accountNumber);
        txn.setDestinationAccount(null);

        transactionRepository.save(txn);
        logger.info("Withdraw successful for account '{}', amount {}", accountNumber, amount);
        return txn;
    }

    // ------------------ TRANSFER ------------------
    public Transaction transfer(String sourceAccountNumber, String destAccountNumber, double amount) {
        logger.info("Transferring {} from '{}' to '{}'", amount, sourceAccountNumber, destAccountNumber);
        if (amount <= 0) {
            logger.warn("Invalid transfer amount: {}", amount);
            throw new InvalidAmountException("Transfer amount must be positive");
        }

        Account source = getAccount(sourceAccountNumber);
        Account dest = getAccount(destAccountNumber);

        if (source.getBalance() < amount) {
            logger.warn("Insufficient balance for transfer from '{}': requested={}, available={}", sourceAccountNumber, amount, source.getBalance());
            throw new InsufficientBalanceException("Insufficient balance");
        }

        source.setBalance(source.getBalance() - amount);
        dest.setBalance(dest.getBalance() + amount);

        accountRepository.save(source);
        accountRepository.save(dest);

        Transaction txn = new Transaction();
        txn.setTransactionId(generateTransactionId());
        txn.setType("TRANSFER");
        txn.setAmount(amount);
        txn.setStatus("SUCCESS");
        txn.setTimestamp(new Date());
        txn.setSourceAccount(sourceAccountNumber);
        txn.setDestinationAccount(destAccountNumber);

        transactionRepository.save(txn);
        logger.info("Transfer successful: {} -> {} amount {}", sourceAccountNumber, destAccountNumber, amount);
        return txn;
    }

    // ------------------ GET TRANSACTIONS ------------------
    public List<Transaction> getTransactions(String accountNumber) {
        logger.info("Fetching transactions for account '{}'", accountNumber);
        getAccount(accountNumber); // Ensure account exists
        List<Transaction> transactions = transactionRepository.findBySourceAccountOrDestinationAccount(accountNumber, accountNumber);
        logger.info("Transactions fetched for account '{}': count={}", accountNumber, transactions.size());
        return transactions;
    }

    // ------------------ GET ALL ACCOUNTS ------------------
    public List<Account> getAllAccounts() {
        logger.info("Fetching all accounts");
        List<Account> accounts = accountRepository.findAll();
        logger.info("Total accounts found: {}", accounts.size());
        return accounts;
    }

    // ------------------ HELPER METHODS ------------------
    private String generateAccountNumber(String holderName) {
        Random rand = new Random();
        String initials = holderName.substring(0, Math.min(3, holderName.length())).toUpperCase();
        int number = rand.nextInt(9000) + 1000; // 1000 - 9999
        return initials + number;
    }

    private String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis();
    }
}
