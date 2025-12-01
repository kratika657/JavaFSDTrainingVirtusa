package com.java.virtusa.model;

import com.java.virtusa.exception.InsufficientBalanceException;
import com.java.virtusa.exception.InvalidAmountException;

public class Accounts {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private boolean isActive = true;

    public boolean isActive() {
        return isActive;
    }

    public void closeAccount() {
        this.isActive = false;
    }

    public Accounts(String accountNumber, String accountHolderName) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = 0.0;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public String getAccountHolderName() {
        return accountHolderName;
    }
    public double getBalance() {
        return balance;
    }
    public synchronized void deposit(double amount) throws InvalidAmountException {
        if (!isActive)
            throw new InvalidAmountException("Account is closed. Cannot deposit.");
        if (amount <= 0)
            throw new InvalidAmountException("Amount must be greater than zero");

        balance += amount;
    }
    public synchronized void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (!isActive)
            throw new InvalidAmountException("Account is closed. Cannot withdraw.");
        if (amount <= 0)
            throw new InvalidAmountException("Amount must be greater than zero");
        if (amount > balance)
            throw new InsufficientBalanceException("Insufficient funds");

        balance -= amount;
    }
    public void showAccountDetails() {
        System.out.println("\n-------------------------------");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: " + balance);
        System.out.println("Status         : " + (isActive ? "Active" : "Closed"));
        System.out.println("-------------------------------");
    }
}

