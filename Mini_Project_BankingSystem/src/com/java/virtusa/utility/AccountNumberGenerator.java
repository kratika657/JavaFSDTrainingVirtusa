package com.java.virtusa.utility;

import java.util.Random;

public class AccountNumberGenerator {
    public static String generate(String name) {
        String initials = name.length() >= 2 ? name.substring(0, 2).toUpperCase() : "AC";
        int randomNum = 1000 + new Random().nextInt(9000);
        return initials + randomNum;
    }
}
