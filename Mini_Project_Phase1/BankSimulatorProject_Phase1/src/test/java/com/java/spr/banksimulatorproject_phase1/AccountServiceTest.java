package com.java.spr.banksimulatorproject_phase1;

import com.java.spr.banksimulatorproject_phase1.exception.AccountNotFoundException;
import com.java.spr.banksimulatorproject_phase1.exception.InsufficientBalanceException;
import com.java.spr.banksimulatorproject_phase1.exception.InvalidAmountException;
import com.java.spr.banksimulatorproject_phase1.model.Account;
import com.java.spr.banksimulatorproject_phase1.model.Transaction;
import com.java.spr.banksimulatorproject_phase1.repo.AccountRepository;
import com.java.spr.banksimulatorproject_phase1.repo.TransactionRepository;
import com.java.spr.banksimulatorproject_phase1.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account1;
    private Account account2;

    @BeforeEach
    public void setUp() {
        account1 = new Account("ACC1001", "Boss");
        account1.setBalance(1000);

        account2 = new Account("ACC1002", "Alice");
        account2.setBalance(500);
    }

    // ------------------ CREATE ACCOUNT ------------------
    @Test
    public void testCreateAccount() {
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArgument(0));

        Account created = accountService.createAccount("TestUser");

        assertNotNull(created.getAccountNumber());
        assertEquals("TestUser", created.getHolderName());
        assertEquals(0.0, created.getBalance());
        assertEquals("ACTIVE", created.getStatus());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    // ------------------ DEPOSIT ------------------
    @Test
    public void testDeposit() {
        when(accountRepository.findByAccountNumber("ACC1001")).thenReturn(Optional.of(account1));
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArgument(0));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArgument(0));

        Transaction txn = accountService.deposit("ACC1001", 500);

        assertEquals(500, txn.getAmount());
        assertEquals("SUCCESS", txn.getStatus());
        assertEquals(1500, account1.getBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testDepositInvalidAmount() {
        assertThrows(InvalidAmountException.class, () -> accountService.deposit("ACC1001", -100));
    }

    // ------------------ WITHDRAW ------------------
    @Test
    public void testWithdraw() {
        when(accountRepository.findByAccountNumber("ACC1001")).thenReturn(Optional.of(account1));
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArgument(0));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArgument(0));

        Transaction txn = accountService.withdraw("ACC1001", 400);

        assertEquals(400, txn.getAmount());
        assertEquals("SUCCESS", txn.getStatus());
        assertEquals(600, account1.getBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testWithdrawInsufficientBalance() {
        when(accountRepository.findByAccountNumber("ACC1002")).thenReturn(Optional.of(account2));
        assertThrows(InsufficientBalanceException.class, () -> accountService.withdraw("ACC1002", 1000));
    }

    @Test
    public void testWithdrawInvalidAmount() {
        assertThrows(InvalidAmountException.class, () -> accountService.withdraw("ACC1001", -50));
    }

    // ------------------ TRANSFER ------------------
    @Test
    public void testTransfer() {
        when(accountRepository.findByAccountNumber(anyString())).thenAnswer(invocation -> {
            String accountNumber = invocation.getArgument(0);
            if (accountNumber.equals("ACC1001")) return Optional.of(account1);
            if (accountNumber.equals("ACC1002")) return Optional.of(account2);
            return Optional.empty();
        });

        when(accountRepository.save(any(Account.class))).thenAnswer(i -> i.getArgument(0));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArgument(0));

        Transaction txn = accountService.transfer("ACC1001", "ACC1002", 300);

        assertEquals("SUCCESS", txn.getStatus());
        assertEquals(700, account1.getBalance());
        assertEquals(800, account2.getBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testTransferInsufficientBalance() {
        when(accountRepository.findByAccountNumber(anyString())).thenAnswer(invocation -> {
            String accountNumber = invocation.getArgument(0);
            if (accountNumber.equals("ACC1001")) return Optional.of(account1);
            if (accountNumber.equals("ACC1002")) return Optional.of(account2);
            return Optional.empty();
        });
        assertThrows(InsufficientBalanceException.class, () -> accountService.transfer("ACC1002", "ACC1001", 1000));
    }

    @Test
    public void testTransferInvalidAmount() {
        // ❌ No stubbing here — avoids UnnecessaryStubbingException
        assertThrows(InvalidAmountException.class,
                () -> accountService.transfer("ACC1001", "ACC1002", -200));
    }

    // ------------------ GET TRANSACTIONS ------------------
    @Test
    public void testGetTransactions() {
        Transaction t1 = new Transaction();
        t1.setTransactionId("TXN1");
        Transaction t2 = new Transaction();
        t2.setTransactionId("TXN2");

        when(accountRepository.findByAccountNumber("ACC1001")).thenReturn(Optional.of(account1));
        when(transactionRepository.findBySourceAccountOrDestinationAccount("ACC1001", "ACC1001"))
                .thenReturn(Arrays.asList(t1, t2));

        List<Transaction> txns = accountService.getTransactions("ACC1001");
        assertEquals(2, txns.size());
    }

    // ------------------ ACCOUNT NOT FOUND ------------------
    @Test
    public void testGetAccountNotFound() {
        when(accountRepository.findByAccountNumber("NONEXISTENT")).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount("NONEXISTENT"));
    }
}
