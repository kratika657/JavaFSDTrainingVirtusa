package com.java.bank.controller;

import com.java.bank.model.Transaction;
import com.java.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    // Deposit
    @PostMapping("/deposit")
    public Transaction deposit(@RequestParam String accountNumber,
                               @RequestParam double amount) {
        return service.deposit(accountNumber, amount);
    }
    @PostMapping("/withdraw")
    public Transaction withdraw(@RequestParam String accountNumber,
                                @RequestParam double amount) {
        return service.withdraw(accountNumber, amount);
    }
    @PostMapping("/transfer")
    public Transaction transfer(@RequestParam String fromAccount,
                                @RequestParam String toAccount,
                                @RequestParam double amount) {
        return service.transfer(fromAccount, toAccount, amount);
    }
    @GetMapping("/account/{accountNumber}")
    public List<Transaction> getTransactions(@PathVariable String accountNumber) {
        return service.getTransactions(accountNumber);
    }
}
