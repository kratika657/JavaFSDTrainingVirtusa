package com.java.spr.banksimulatorproject_phase1;

import com.java.spr.banksimulatorproject_phase1.model.Account;
import com.java.spr.banksimulatorproject_phase1.repo.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AccountRepositoryTest {

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void testFindByAccountNumber() {
        Account acc = new Account("ACC1010", "RepoUser");
        when(accountRepository.findByAccountNumber("ACC1010")).thenReturn(Optional.of(acc));

        Optional<Account> found = accountRepository.findByAccountNumber("ACC1010");
        assertTrue(found.isPresent());
        assertEquals("RepoUser", found.get().getHolderName());
        verify(accountRepository, times(1)).findByAccountNumber("ACC1010");
    }

    @Test
    public void testExistsByAccountNumber() {
        when(accountRepository.existsByAccountNumber("ACC1011")).thenReturn(true);
        assertTrue(accountRepository.existsByAccountNumber("ACC1011"));
        verify(accountRepository, times(1)).existsByAccountNumber("ACC1011");
    }

    @Test
    public void testDeleteByAccountNumber() {
        doNothing().when(accountRepository).deleteByAccountNumber("ACC1012");
        accountRepository.deleteByAccountNumber("ACC1012");
        verify(accountRepository, times(1)).deleteByAccountNumber("ACC1012");
    }
}
