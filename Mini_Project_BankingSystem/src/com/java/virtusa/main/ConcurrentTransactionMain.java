package com.java.virtusa.main;

import com.java.virtusa.dao.BankDaoImpl;
import com.java.virtusa.model.Accounts;

public class ConcurrentTransactionMain {
    public static void main(String[] args) throws InterruptedException {
        BankDaoImpl bankDao = new BankDaoImpl();

        Accounts acc = new Accounts("KR2156", "Kratika");
        bankDao.addAccount(acc);

        Thread t1 = new Thread(() -> {
            try {
                acc.deposit(1000);
                System.out.println("Thread 1: Deposited 1000");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                acc.withdraw(500);
                System.out.println("Thread 2: Withdrawn 500");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final Account Balance: " + acc.getBalance());
    }
}
