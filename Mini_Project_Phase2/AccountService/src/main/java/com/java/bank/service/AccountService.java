package com.java.bank.service;

import com.java.bank.model.Account;
import com.java.bank.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    public Account createAccount(Account account) {
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(0.0);
        account.setActive(true);
        return repository.save(account);
    }

    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis();
    }

    public Account getAccount(String accountNumber) {
        return repository.findByAccountNumber(accountNumber);
    }

    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    public Account updateBalance(String accountNumber, double amount) {
        Account account = repository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }
        account.setBalance(account.getBalance() + amount);
        return repository.save(account);
    }

    public Account updateStatus(String accountNumber, boolean status) {
        Account account = repository.findByAccountNumber(accountNumber);

        if (account == null) {
            throw new RuntimeException("Account not found: " + accountNumber);
        }

        account.setActive(status);
        return repository.save(account);
    }
}
