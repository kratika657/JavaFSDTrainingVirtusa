package com.java.virtusa.main;

import com.java.virtusa.dao.BankDaoImpl;
import com.java.virtusa.model.Accounts;
import com.java.virtusa.utility.AccountNumberGenerator;

import java.util.Scanner;

public class BankMainMenu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankDaoImpl bankDao = new BankDaoImpl();
        boolean running = true;

        while (running) {
            System.out.println("\n===== BANKING SYSTEM MENU =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Show Balance");
            System.out.println("6. Close Account");
            System.out.println("7. Show All Accounts");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
                continue;
            }

            switch (choice) {

                case 1:
                    sc.nextLine();
                    System.out.print("Enter Account Holder Name: ");
                    String name = sc.nextLine();
                    if (name.trim().isEmpty()) {
                        System.out.println("Name cannot be empty!");
                        break;
                    }
                    String accNum = AccountNumberGenerator.generate(name);
                    Accounts acc = new Accounts(accNum, name);
                    bankDao.addAccount(acc);
                    System.out.println("Account created successfully!");
                    acc.showAccountDetails();
                    break;

                case 2: // Deposit
                    System.out.print("Enter Account Number: ");
                    String dNum = sc.next();
                    try {
                        Accounts dAcc = bankDao.getAccount(dNum);
                        System.out.print("Enter Deposit Amount: ");
                        double amount = sc.nextDouble();
                        dAcc.deposit(amount);
                        System.out.println("Amount Deposited Successfully");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3: // Withdraw
                    System.out.print("Enter Account Number: ");
                    String wNum = sc.next();
                    try {
                        Accounts wAcc = bankDao.getAccount(wNum);
                        System.out.print("Enter Withdrawal Amount: ");
                        double amount = sc.nextDouble();
                        wAcc.withdraw(amount);
                        System.out.println("Amount Withdrawn Successfully");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4: // Transfer
                    sc.nextLine();
                    System.out.print("Enter Sender Account Number: ");
                    String from = sc.nextLine().trim();
                    System.out.print("Enter Receiver Account Number: ");
                    String to = sc.nextLine().trim();
                    System.out.print("Enter Transfer Amount: ");
                    double amt = sc.nextDouble();

                    try {
                        Accounts sender = bankDao.getAccount(from);
                        Accounts receiver = bankDao.getAccount(to);

                        if (sender.getBalance() < amt) {
                            System.out.println("Insufficient Balance in Sender Account!");
                            break;
                        }
                        sender.withdraw(amt);
                        receiver.deposit(amt);
                        System.out.println("Transfer Successful!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5: // Show Balance
                    System.out.print("Enter Account Number: ");
                    String sNum = sc.next();
                    try {
                        Accounts sAcc = bankDao.getAccount(sNum);
                        sAcc.showAccountDetails();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 6: // Close Account
                    System.out.print("Enter Account Number: ");
                    String cNum = sc.next();
                    try {
                        bankDao.removeAccount(cNum);
                        System.out.println("Account Closed Successfully");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 7:
                    bankDao.getAllAccounts()
                            .stream().forEach(Accounts::showAccountDetails);
                    break;

                case 8:
                    running = false;
                    System.out.println("Thank you for using the Banking System");
                    break;


                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
        sc.close();
    }
}
