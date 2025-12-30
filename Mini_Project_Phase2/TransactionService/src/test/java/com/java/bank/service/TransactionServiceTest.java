package com.java.bank.service;

import com.java.bank.client.AccountClient;
import com.java.bank.client.NotificationClient;
import com.java.bank.dto.AccountDTO;
import com.java.bank.model.Transaction;
import com.java.bank.repo.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private AccountClient accountClient;

    @Mock
    private NotificationClient notificationClient;

    @Mock
    private TransactionRepository transactionRepository;

    // 1️⃣ Deposit success
    @Test
    void deposit_success() {
        AccountDTO acc = new AccountDTO();
        acc.setBalance(10000);

        Mockito.when(accountClient.updateBalance("ACC1", 5000))
                .thenReturn(acc);

        Transaction txn = transactionService.deposit("ACC1", 5000);

        assertEquals("SUCCESS", txn.getStatus());
        assertEquals("DEPOSIT", txn.getType());
    }

    // 2️⃣ Deposit failure
    @Test
    void deposit_failure() {
        Mockito.when(accountClient.updateBalance(Mockito.any(), Mockito.anyDouble()))
                .thenThrow(new RuntimeException("AccountService down"));

        Transaction txn = transactionService.deposit("ACC1", 5000);

        assertEquals("FAILED", txn.getStatus());
    }

    // 3️⃣ Transfer success
    @Test
    void transfer_success() {
        AccountDTO acc = new AccountDTO();
        acc.setBalance(10000);

        Mockito.when(accountClient.updateBalance("A1", -2000)).thenReturn(acc);
        Mockito.when(accountClient.updateBalance("A2", 2000)).thenReturn(acc);

        Transaction txn = transactionService.transfer("A1", "A2", 2000);

        assertEquals("SUCCESS", txn.getStatus());
        assertEquals("TRANSFER", txn.getType());
    }
}
