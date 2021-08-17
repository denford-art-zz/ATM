package com.denisdedov;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;
    private Object Integer;

    /**
     * Create a new Bank object with lists of users and accounts.
     * @param name the name of the bank.
     */
    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Generate a new UUID for a user.
     * @return
     */
    public String getNewUserUUID() {
        String uuid;
        Random random = new Random();
        int length = 6;
        boolean nonUnique;

        do {
            // generate the UUID
            uuid = "";
            for (int i = 0; i < length; i++) {
                uuid += ((Integer)random.nextInt(10)).toString();
            }

            // checks the uniqueness of the id
            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
    }

    /**
     * Generate a new UUID for an account.
     * @return the UUID.
     */
    public String getNewAccountUUID() {

        String uuid;
        Random random = new Random();
        int length = 10;
        boolean nonUnique;

        do {
            // generate the UUID
            uuid = "";
            for (int i = 0; i < length; i++) {
                uuid += ((Integer)random.nextInt(10)).toString();
            }

            // checks the uniqueness of the id
            nonUnique = false;
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
    }

    /**
     * Create a new user of the bank.
     * @param firstName the user's first name.
     * @param lastName the user's last name.
     * @param pin the user's pin code.
     * @return the new User object.
     */
    public User addUser(String firstName, String lastName, String pin) {
        // create a new User object
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        Account newAccount = new Account("Hendrix", newUser, this);
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;
    }

    /**
     * add user's account.
     * @param anAcc the account to add.
     */
    public void addAccount(Account anAcc) {
        this.accounts.add((anAcc));
    }

    /**
     * Get the USer object associated with a particular userID and pin code,
     * if they are valid
     * @param userID the UUID of the user
     * @param pin the pin code of the user
     * @return the User object, if the login is successful,
     * or null, if it is not
     */
    public User userLogin(String userID, String pin) {

        for (User user : this.users) {

            // check user ID is correct
            if (user.getUUID().compareTo(userID) == 0 && user.validatePin(pin)) {
                return user;
            }
        }
        // if user not found or incorrect pin
        return null;
    }

    public String getName() {
        return this.name;
    }
}
