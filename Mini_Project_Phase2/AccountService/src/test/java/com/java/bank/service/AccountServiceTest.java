package com.java.bank.service;

import com.java.bank.model.Account;
import com.java.bank.repo.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountService service;

    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        account = new Account();
        account.setId("1");
        account.setAccountNumber("ACC123");
        account.setHolderName("John Doe");
        account.setBalance(1000.0);
        account.setActive(true);
    }

    @Test
    void testCreateAccount() {
        Mockito.when(repository.save(Mockito.any(Account.class))).thenReturn(account);

        Account created = service.createAccount(new Account());
        assertNotNull(created);
        assertEquals("John Doe", created.getHolderName()); // note: in actual creation, holderName is set by you
    }

    @Test
    void testGetAccount() {
        Mockito.when(repository.findByAccountNumber("ACC123")).thenReturn(account);

        Account found = service.getAccount("ACC123");
        assertNotNull(found);
        assertEquals("ACC123", found.getAccountNumber());
    }

    @Test
    void testGetAllAccounts() {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(account));

        List<Account> accounts = service.getAllAccounts();
        assertEquals(1, accounts.size());
    }

    @Test
    void testUpdateBalance() {
        Mockito.when(repository.findByAccountNumber("ACC123")).thenReturn(account);
        Mockito.when(repository.save(Mockito.any(Account.class))).thenReturn(account);

        Account updated = service.updateBalance("ACC123", 500.0);
        assertEquals(1500.0, updated.getBalance());
    }

    @Test
    void testUpdateStatus() {
        Mockito.when(repository.findByAccountNumber("ACC123")).thenReturn(account);
        Mockito.when(repository.save(Mockito.any(Account.class))).thenReturn(account);

        Account updated = service.updateStatus("ACC123", false);
        assertFalse(updated.isActive());
    }

    @Test
    void testUpdateBalanceAccountNotFound() {
        Mockito.when(repository.findByAccountNumber("ACC999")).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.updateBalance("ACC999", 100.0);
        });

        assertEquals("Account not found", exception.getMessage());
    }

    @Test
    void testUpdateStatusAccountNotFound() {
        Mockito.when(repository.findByAccountNumber("ACC999")).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.updateStatus("ACC999", true);
        });

        assertEquals("Account not found: ACC999", exception.getMessage());
    }
}
