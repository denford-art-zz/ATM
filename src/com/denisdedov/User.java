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

        this.uuid = theBank.getNewUserUUID();
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

    public String getFirstName() { return  this.firstName; }

    /**
     * сhecking the variability of the pin code
     * @param pin the pin to check
     * @return correct or incorrect pin code
     */
    public boolean validatePin(String pin) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()),
                    this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error, NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        return false;
    }

    /**
     * Print summaries for the accounts of this user.
     */
    public void printAccountsSummary() {
        System.out.printf("\n%s's account summary\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("%d) %s\n", a + 1,
                    this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    public int numAccounts() {
        return this.accounts.size();
    }

    /**
     * Print transaction history for a particular account.
     * @param accountIndex the index of the account to use.
     */
    public void printAccountsHistory(int accountIndex) {
        this.accounts.get(accountIndex).printTransactionsHistory();
    }

    public double getAccountBalance(int accountIndex) {
        return  this.accounts.get(accountIndex).getBalance();
    }

    public String getAccountUUID(int accountIndex) {
        return this.accounts.get(accountIndex).getUUID();
    }

    public void addAccountTransaction(
            int accountIndex, double amount, String memo) {
        this.accounts.get(accountIndex).addTransaction(amount, memo);
    }
}
