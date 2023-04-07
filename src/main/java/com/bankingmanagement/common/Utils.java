package com.bankingmanagement.common;

import java.security.SecureRandom;

public class Utils {
    private static final String DIGITS = "123456789";

    public static String generateTransactionID() {
        return "TXN" + nextRandomString(9);
    }

    public static String generateCustomerID() {
        return nextRandomString(8);
    }

    public static String generateAccountNumber() {
        return nextRandomString(14);
    }

    private static String nextRandomString(int length) {
        String characters = DIGITS;
        char[] buffer = new char[length];
        for (int i = 0; i < buffer.length; ++i)
            buffer[i] = characters.toCharArray()[new SecureRandom().nextInt(characters.length())];
        return new String(buffer);
    }
//    public static void main(String[] args) {
//
//        String tickets =  generateTransactionID();
//        System.out.println(tickets);
//        System.out.println(generateCustomerID());
//        System.out.println(generateAccountNumber());
//    }
}
