package com.pluralsight;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class Credentials {
    public static void main(String[] args) {
        System.out.println(CREDENTIALS.entrySet());
        System.out.println(CREDENTIALS);
    }

    public static final Map<String, String> CREDENTIALS = loadCredentials();
    private static final String CREDENTIALS_FILE = "credentials.csv";

    private static HashMap<String, String> loadCredentials() {
        var credentials = new HashMap<String, String>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            String fileLine;
            while ((fileLine = bufferedReader.readLine()) != null) {
                String[] lineArr = fileLine.split(Pattern.quote("|"));
                String userName = lineArr[0];
                String hashPw = lineArr[1];
                credentials.put(userName, hashPw);
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            return credentials;
        }
    }

    public static void storeCredentials(String userName, String password) {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(CREDENTIALS_FILE, true))) {
            bufferedWriter.append(String.format("%s|%s\n", userName, hashPassword(password)));
            CREDENTIALS.put(userName,hashPassword(password));
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static String hashPassword(String passwordToBeHashed) {
        String hashedPassword = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(passwordToBeHashed.getBytes());
            hashedPassword = Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } finally {
            return hashedPassword;
        }
    }

    public static boolean hasAccount(String userName){
        return CREDENTIALS.keySet().contains(userName) ? true : false;
    }

}
