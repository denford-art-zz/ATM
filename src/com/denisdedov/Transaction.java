package com.denisdedov;

import java.util.Date;

public class Transaction {
    private double amount;
    private Date timestamp;
    private String memo;
    private Account isAccount;

    /**
     * Create a new transaction
     * @param amount the amount transacted
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

    public double getAmount() {
        return this.amount;
    }

    /**
     * Get a string summarizing the transaction
     * @return the summary string
     */
    public String getSummaryLine() {
        if (this.amount >= 0) {
            return String.format("%s : $%.02f : %s",
                    this.timestamp.toString(), this.amount, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s",
                    this.timestamp.toString(), this.amount, this.memo);
        }
    }
}
