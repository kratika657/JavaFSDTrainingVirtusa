package com.java.bank.controller;

import com.java.bank.model.Account;
import com.java.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService service;

    // Create a new account
    @PostMapping
    public ResponseEntity<Account> create(@RequestBody Account account) {
        Account created = service.createAccount(account);
        return ResponseEntity.ok(created);
    }

    // Get account by account number
    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> get(@PathVariable String accountNumber) {
        Account account = service.getAccount(accountNumber);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }

    // Update balance of an account
    @PutMapping("/{accountNumber}/balance")
    public ResponseEntity<Account> updateBalance(@PathVariable String accountNumber,
                                                 @RequestParam double amount) {
        Account updated = service.updateBalance(accountNumber, amount);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // Update account status (active/inactive)
    @PutMapping("/{accountNumber}/status")
    public ResponseEntity<Account> updateStatus(@PathVariable String accountNumber,
                                                @RequestParam boolean active) {
        Account updated = service.updateStatus(accountNumber, active);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // Get all accounts
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = service.getAllAccounts();
        if (accounts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accounts);
    }
}
