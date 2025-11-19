package com.java.leave.main;

import com.java.leave.Bal.LeaveBal;
import com.java.leave.exception.LeaveException;
import com.java.leave.model.LeaveDetails;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Leavemain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LeaveBal bal = new LeaveBal();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int choice;
        do {
            System.out.println("1. Add Leave");
            System.out.println("2. Show Leave");
            System.out.println("3. Search Leave");
            System.out.println("4. Update Leave");
            System.out.println("5. Delete Leave");
            System.out.println("6. Exit");

            choice = sc.nextInt();
            switch (choice) {

                case 1:
                    try {
                        LeaveDetails ld = new LeaveDetails();

                        System.out.println("Enter Leave Id:");
                        ld.setLeaveId(sc.nextInt());

                        System.out.println("Enter Emp Id:");
                        ld.setEmpId(sc.nextInt());

                        System.out.println("Enter Start Date (yyyy-MM-dd):");
                        ld.setLeaveStartDate(sdf.parse(sc.next()));

                        System.out.println("Enter End Date (yyyy-MM-dd):");
                        ld.setLeaveEndDate(sdf.parse(sc.next()));

                        System.out.println("Enter Reason:");
                        sc.nextLine();
                        ld.setLeaveReason(sc.nextLine());

                        System.out.println(bal.addLeaveBal(ld));

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    List<LeaveDetails> list = bal.showLeaveBal();
                    if (list.isEmpty()) {
                        System.out.println("No leave records available.");
                    }
                    else {
                        for (LeaveDetails ll : list) {
                            System.out.println(ll);
                        }
                    }
                    break;

                case 3:
                    System.out.println("Enter Leave Id:");
                    int searchId = sc.nextInt();
                    LeaveDetails l = bal.searchLeaveBal(searchId);
                    System.out.println(l != null ? l : "Not Found");
                    break;

                case 4:
                    try {
                        LeaveDetails up = new LeaveDetails();

                        System.out.println("Enter Leave Id:");
                        up.setLeaveId(sc.nextInt());

                        System.out.println("Enter Emp Id:");
                        up.setEmpId(sc.nextInt());

                        System.out.println("Enter Start Date:");
                        up.setLeaveStartDate(sdf.parse(sc.next()));

                        System.out.println("Enter End Date:");
                        up.setLeaveEndDate(sdf.parse(sc.next()));

                        System.out.println("Enter Reason:");
                        sc.nextLine();
                        up.setLeaveReason(sc.nextLine());

                        System.out.println(bal.updateLeaveBal(up));

                    } catch (LeaveException e) {
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Invalid Input");
                    }
                    break;

                case 5:
                    System.out.println("Enter Leave Id:");
                    int delId = sc.nextInt();
                    System.out.println(bal.deleteLeaveBal(delId));
                    break;
            }

        } while (choice != 6);
    }
}
