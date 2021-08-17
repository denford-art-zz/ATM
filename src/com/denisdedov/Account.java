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

    /**
     * Get summary line for the account
     * @return the string summary
     */
    public String getSummaryLine() {
        double balance = this.getBalance();

        if (balance >= 0) {
            return String.format("%s: $%.02f : %s",
                    this.uuid, balance, this.name);
        } else {
            return String.format("%s: $(%.02f) : %s",
                    this.uuid, balance, this.name);
        }
    }

    public double getBalance() {

        double balance = 0;
        for (Transaction transaction : this.transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }
    public void printTransactionsHistory() {
        System.out.printf("\nTransaction history for account %s\n",
                this.uuid);

        for (int t = this.transactions.size()-1; t >= 0; t--) {
            System.out.printf(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    public void addTransaction(double amount, String memo) {
        Transaction newTransaction = new Transaction(amount, memo, this);
        this.transactions.add(newTransaction);
    }
}
