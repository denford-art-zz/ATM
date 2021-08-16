package com.denisdedov;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Bank theBank = new Bank("Bank of Valhalla");

        User user = theBank.addUser("Thor", "Odinson", "1234");
        Account newAccount = new Account("Odin", user, theBank);
        user.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {

            curUser = ATM.mainMenuPromt(theBank, scanner);
            ATM.printUserMenu(curUser, scanner);
        }
    }

    /**
     * Enter welcome text
     * @param theBank
     * @param scanner
     * @return
     */
    public static User mainMenuPromt(Bank theBank, Scanner scanner) {
        String userID;
        String pin;
        User authUser;

        do {
            System.out.printf("\nWelcome to %s\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = scanner.nextLine();
            System.out.print("Enter pin: ");
            pin = scanner.nextLine();

            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID or pin.\nPlease try again.");
            }

        } while(authUser == null);
        return authUser;
    }

    public static void printUserMenu(User user, Scanner scanner) {
        user.printAccountsSummary();

        int choice;

        do {
            System.out.printf("Welcome %s, what would you like to do?",
                    user.getFirstName());
            System.out.println("\n1) Show account transaction history\n" +
                    "2) Withdraw\n" +
                    "3) Deposit\n" +
                    "4) Transfer\n" +
                    "5) Quit");
            System.out.println("Enter choice: ");
            choice = scanner.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid value. Please, choose 1-5");
            }
        } while (choice < 1 || choice > 5);

        switch (choice) {
            case 1:
                ATM.showTransactionHistory(user, scanner);
                break;
            case 2:
                ATM.withdrawFunds(user, scanner);
                break;
            case 3:
                ATM.depositFunds(user, scanner);
                break;
            case 4:
                ATM.transferFunds(user, scanner);
                break;
            case 5:
                scanner.nextLine();
                break;

        }

        if (choice != 5) {
            ATM.printUserMenu(user, scanner);
        }
    }

    public static void showTransactionHistory(User user, Scanner scanner) {

        int theAcc;

        do {
            System.out.printf("Enter the number(1-%d) of the account",
                    user.numAccounts());
            theAcc = scanner.nextInt()-1;
            if (theAcc < 0 || theAcc >= user.numAccounts()) {
                System.out.println("Invalid value");
            }
        } while (theAcc < 0 || theAcc >= user.numAccounts());

        user.printAccountsHistory(theAcc);
    }

    public static void transferFunds(User user, Scanner scanner) {

        int fromAccount;
        int toAccount;
        double amount;
        double accountBalance;

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer from: ", user.numAccounts());
            fromAccount = scanner.nextInt()-1;
            if (fromAccount < 0 || fromAccount >= user.numAccounts()) {
                System.out.println("Incorrect user ID or pin.\n" +
                        "Please try again.");
            }
        } while(fromAccount < 0 || fromAccount >= user.numAccounts());
        accountBalance = user.getAccountBalance(fromAccount);

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer to: ", user.numAccounts());
            toAccount = scanner.nextInt()-1;
            if (toAccount < 0 || toAccount >= user.numAccounts()) {
                System.out.println("Incorrect user ID or pin.\n" +
                        "Please try again.");
            }
        } while(toAccount < 0 || toAccount >= user.numAccounts());

        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    accountBalance);
            amount = scanner.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than 0");
            } else if (amount > accountBalance) {
                System.out.printf("Amount not can greater than balance\n" +
                        "Balance: $%.02f", accountBalance);
            }
        } while (amount < 0 || amount > accountBalance);

        user.addAccountTransaction(fromAccount, -1*amount,
                String.format("Transfer to account %s",
                        user.getAccountUUID(toAccount)));
        user.addAccountTransaction(toAccount, amount,
                String.format("Transfer to account %s",
                        user.getAccountUUID(fromAccount)));
    }

    public static void withdrawFunds(User user, Scanner scanner) {

        int fromAccount;
        double amount;
        double accountBalance;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to withdraw from: ", user.numAccounts());
            fromAccount = scanner.nextInt()-1;
            if (fromAccount < 0 || fromAccount >= user.numAccounts()) {
                System.out.println("Incorrect user ID or pin.\n" +
                        "Please try again.");
            }
        } while(fromAccount < 0 || fromAccount >= user.numAccounts());
        accountBalance = user.getAccountBalance(fromAccount);

        do {
            System.out.printf("\nEnter the amount to transfer (max $%.02f): $",
                    accountBalance);
            amount = scanner.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than 0");
            } else if (amount > accountBalance) {
                System.out.printf("Amount not can greater than balance\n" +
                        "Balance: $%.02f", accountBalance);
            }
        } while (amount < 0 || amount > accountBalance);

        scanner.nextLine();

        System.out.println("Enter a memo: ");
        memo = scanner.nextLine();

        user.addAccountTransaction(fromAccount, -1*amount, memo);

    }

    public static void depositFunds(User user, Scanner scanner) {
        int toAccount;
        double amount;
        double accountBalance;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to deposit in: ", user.numAccounts());
            toAccount = scanner.nextInt()-1;
            if (toAccount < 0 || toAccount >= user.numAccounts()) {
                System.out.println("Incorrect user ID or pin.\n" +
                        "Please try again.");
            }
        } while(toAccount < 0 || toAccount >= user.numAccounts());
        accountBalance = user.getAccountBalance(toAccount);

        do {
            System.out.printf("\nEnter the amount to transfer (max $%.02f): $",
                    accountBalance);
            amount = scanner.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than 0");
            }
        } while (amount < 0);

        scanner.nextLine();

        System.out.println("Enter a memo: ");
        memo = scanner.nextLine();

        user.addAccountTransaction(toAccount, amount, memo);
    }
}