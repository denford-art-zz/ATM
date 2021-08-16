package com.denisdedov;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account> accounts;

    /**
     * Create a new user.
     * @param firstName the user's first name.
     * @param lastName the user's last name.
     * @param pin the user's pin code.
     * @param theBank the bank that the user is a client of.
     */
    public User(String firstName, String lastName, String pin, Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error, NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        this.accounts = new ArrayList<Account>();

        System.out.printf("User %s, %s with ID %s. \n",
                lastName, firstName, this.uuid);
    }

    /**
     * Add an account for the user.
     * @param anAcc the account being added.
     */
    public void addAccount(Account anAcc) {
        this.accounts.add(anAcc);
    }

    /**
     * Return the user's UUID.
     * @return the UUID.
     */
    public String getUUID() {
        return this.uuid;
    }
}
