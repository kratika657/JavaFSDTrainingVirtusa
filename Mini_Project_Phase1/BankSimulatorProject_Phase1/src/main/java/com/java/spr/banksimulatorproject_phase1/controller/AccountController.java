package com.java.spr.banksimulatorproject_phase1.controller;

import com.java.spr.banksimulatorproject_phase1.model.Account;
import com.java.spr.banksimulatorproject_phase1.model.Transaction;
import com.java.spr.banksimulatorproject_phase1.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    // Create new account
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account accountRequest) {
        logger.info("Request received to create account for holder: {}", accountRequest.getHolderName());
        Account account = accountService.createAccount(accountRequest.getHolderName());
        logger.info("Account created successfully with Account Number: {}", account.getAccountNumber());
        return ResponseEntity.status(201).body(account);
    }

    // Get account by accountNumber
    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        logger.info("Fetching account details for Account Number: {}", accountNumber);
        Account account = accountService.getAccount(accountNumber);
        logger.info("Account retrieved successfully: {}", accountNumber);
        return ResponseEntity.ok(account);
    }

    // Deposit into account
    @PutMapping("/{accountNumber}/deposit")
    public ResponseEntity<Transaction> deposit(@PathVariable String accountNumber,
                                               @RequestParam double amount) {
        logger.info("Deposit request: Account={} Amount={}", accountNumber, amount);
        Transaction txn = accountService.deposit(accountNumber, amount);
        logger.info("Deposit successful. Transaction ID: {}", txn.getTransactionId());
        return ResponseEntity.ok(txn);
    }

    // Withdraw from account
    @PutMapping("/{accountNumber}/withdraw")
    public ResponseEntity<Transaction> withdraw(@PathVariable String accountNumber,
                                                @RequestParam double amount) {
        logger.info("Withdraw request: Account={} Amount={}", accountNumber, amount);
        Transaction txn = accountService.withdraw(accountNumber, amount);
        logger.info("Withdraw successful. Transaction ID: {}", txn.getTransactionId());
        return ResponseEntity.ok(txn);
    }

    // Transfer between accounts
    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestParam String sourceAccount,
                                                @RequestParam String destinationAccount,
                                                @RequestParam double amount) {
        logger.info("Transfer request: From={} To={} Amount={}", sourceAccount, destinationAccount, amount);
        Transaction txn = accountService.transfer(sourceAccount, destinationAccount, amount);
        logger.info("Transfer successful. Transaction ID: {}", txn.getTransactionId());
        return ResponseEntity.ok(txn);
    }

    // Get all transactions for an account
    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String accountNumber) {
        logger.info("Fetching transactions for Account: {}", accountNumber);
        List<Transaction> transactions = accountService.getTransactions(accountNumber);
        logger.info("Transactions retrieved successfully for Account: {}", accountNumber);
        return ResponseEntity.ok(transactions);
    }

    // Optional: list all accounts (not in original requirements)
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        logger.info("Request received to fetch all accounts");
        List<Account> accounts = accountService.getAllAccounts();
        logger.info("Total accounts fetched: {}", accounts.size());
        return ResponseEntity.ok(accounts);
    }
}
