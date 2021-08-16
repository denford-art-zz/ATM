package com.denisdedov;

import java.util.Date;

public class Transaction {
    private double amount;
    private Date timestamp;
    private String memo;
    private Account isAccount;

    /**
     * Create a new transaction
     * @param amount the ammount transacted
     * @param isAccount the account the transaction belong to
     */
    public Transaction(double amount, Account isAccount) {
        this.amount = amount;
        this.isAccount = isAccount;
        this.timestamp = new Date();
        this.memo = "";
    }

    public Transaction(double amount, String memo, Account isAccount) {
        this.amount = amount;
        this.isAccount = isAccount;
        this.timestamp = new Date();
        this.memo = memo;
    }
}
