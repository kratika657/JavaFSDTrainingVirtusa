package com.java.virtusa.main;

import com.java.virtusa.dao.BankDaoImpl;
import com.java.virtusa.model.Accounts;
import com.java.virtusa.utility.AccountNumberGenerator;

import java.util.Scanner;

public class CreateAccountMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankDaoImpl bankDao = new BankDaoImpl();

        System.out.println("Enter Account Holder Name:");
        String name = sc.nextLine();

        if(name.isEmpty()) {
            System.out.println("Invalid name!");
            return;
        }
        String accNum = AccountNumberGenerator.generate(name);
        Accounts account = new Accounts(accNum, name);
        bankDao.addAccount(account);

        System.out.println("Account Created Successfully!");
        account.showAccountDetails();
    }
}
