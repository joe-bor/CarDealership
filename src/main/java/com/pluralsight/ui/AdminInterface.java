package com.pluralsight.ui;

import com.pluralsight.data_access.LeaseContractDAO;
import com.pluralsight.data_access.SalesContractDAO;
import com.pluralsight.model.contract.Contract;
import com.pluralsight.ContractFileManager;
import com.pluralsight.model.Credentials;
import com.pluralsight.model.contract.LeaseContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminInterface {

    private final Scanner SCANNER = new Scanner(System.in);
    private List<Contract> contractList;
    private boolean loggedIn = false;
    private boolean displayed = true;

    public void display() {

        do {
            if (loggedIn) {
                adminDisplay();
            } else {
                loginDisplay();
            }
        } while (displayed);
    }

    public void loginDisplay() {

        boolean isShown = true;
        do {
            System.out.println("----- Login Screen -----");
            System.out.println("""
                    [1] - Create admin account
                    [2] - Login as admin
                    [X] - Exit admin display
                    """);

            String options = SCANNER.nextLine().trim();
            switch (options) {
                case "1" -> createAdminAccount();
                case "2" -> {
                    loginAdminAccount();
                    isShown = false;
                }
                case "X" -> {
                    System.out.println("Exiting admin display...");
                    isShown = false;
                }
                default -> System.out.println("\n ** Invalid Option ** \n");
            }
        } while (isShown);
    }

    public void adminDisplay() {
        init();

        boolean isShown = true;

        do {
            System.out.println("""
                    
                    ----- Admin Interface -----
                    [1] - List all contracts
                    [X] - Exit
                    
                    """);

            String option = SCANNER.nextLine().trim();
            switch (option) {
                case "1" -> processListAllContracts();
                case "X" -> {
                    System.out.println("Exiting...");
                    isShown = false;
                    displayed = false;
                }
                default -> System.out.println("Invalid option");
            }

        } while (isShown);
    }

    private void processListAllContracts() {
        for (Contract contract : this.contractList) {
            System.out.println(String.format("%s -- %s -- %s", contract.getContractDate(), contract.getCustomerName(), contract.getCustomerEmail()));
        }
    }


    private void createAdminAccount() {
        System.out.println("Creating a new admin account");
        System.out.print("Provide a username: \n");
        String userName = SCANNER.nextLine();
        System.out.print("Provide a password: \n");
        String password = SCANNER.nextLine();

        if (Credentials.hasAccount(userName)) {
            System.out.println(String.format("Account with %s as username already exists", userName));
        } else {
            Credentials.storeCredentials(userName, password);
            System.out.println("Admin account created");
        }
    }

    private void loginAdminAccount() {
        System.out.print("Username: ");
        String username = SCANNER.nextLine().trim();
        System.out.print("Password: ");
        String password = SCANNER.nextLine().trim();

        // check if the username exists in database
        if (!Credentials.hasAccount(username)) {
            System.out.println("Username does not exist account");
        } else {
            if (Credentials.CREDENTIALS.get(username).equals(Credentials.hashPassword(password))) {
                System.out.println("Welcome Admin");
                this.loggedIn = true;
            } else {
                System.out.println("Invalid account");
            }
        }

    }

    private void init() {
        List<Contract> contracts = new ArrayList<>();
        contracts.addAll(new LeaseContractDAO().readLeaseContracts());
        contracts.addAll(new SalesContractDAO().readSalesContracts());
        setContractList(contracts);
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }
}
