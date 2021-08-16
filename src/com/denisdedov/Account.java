package com.denisdedov;

import java.util.ArrayList;

public class Account{
    private String name;
    private String uuid;
    private User holder;
    private ArrayList<Transaction> transactions;

    /**
     * Create a new Account
     * @param name the account's name
     * @param holder the account's holder
     * @param theBank the bank that issues the account
     */
    public Account(String name, User holder, Bank theBank) {
        this.name = name;
        this.holder = holder;

        this.uuid = theBank.getNewAccountUUID();
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Return the account's UUID.
     * @return the UUID.
     */
    public String getUUID() {
        return this.uuid;
    }
}
