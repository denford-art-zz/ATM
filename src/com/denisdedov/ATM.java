package com.denisdedov;

import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Bank theBank = new Bank("Bank of Valhalla");

        User user = theBank.addUser("Thor", "Odinson", "1234");
        Account newAccount = new Account("Testing", user, theBank);
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
            System.out.println("1) Show account transaction history\n" +
                    "2) Withdraw\n" +
                    "3) Transfer\n" +
                    "4) Quit");
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
                ATM.withdrawlFunds(user, scanner);
                break;
            case 3:
                ATM.depositFunds(user, scanner);
                break;
            case 4:
                ATM.transferFunds(user, scanner);
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
}