package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1;

import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.controller.AccountController;
import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model.Account;
import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model.CheckingAccount;
import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model.Customer;
import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model.SavingsAccount;

public class Main {
    static void main(String[] args) {
        AccountController controller = new AccountController();

        System.out.println("Customer Making");
        Customer person1 = controller.createCustomer("Dylan", "1 Meow St", "person");
        Customer company1 = controller.createCustomer("FDM", "2 Finance St", "company");

        System.out.println("Person created: " + person1.getCUSTOMER_ID() + ",Name: " + person1.getName());
        System.out.println("Company created: " + company1.getCUSTOMER_ID() + ",Name: " + company1.getName());

        System.out.println("Creating Accounts");
        Account checkingAccount = controller.createAccount(person1, "checking");
        Account savingsAccount = controller.createAccount(person1, "savings");
        Account companyChecking = controller.createAccount(company1, "checking");
        Account companySavings = controller.createAccount(company1, "savings");

        System.out.println("Checking Account created: " + checkingAccount.getACCOUNT_ID());
        System.out.println("Savings Account created: " + savingsAccount.getACCOUNT_ID());
        System.out.println("Company Checking Account created: " + companyChecking.getACCOUNT_ID());
        System.out.println("Company Savings Account created: " + companySavings.getACCOUNT_ID());

        System.out.println("Testing Deposit and Withdraw");
        checkingAccount.deposit(1000);
        System.out.println("Deposit Successful, Balance: " + checkingAccount.getBalance());

        double withdrawn = checkingAccount.withdraw(200);
        System.out.println("Withdrawal Successful, Balance: " + checkingAccount.getBalance());

        savingsAccount.deposit(5000);
        ((SavingsAccount) savingsAccount).setInterestRate(5.0);
        System.out.println("Savings account balance before interest: " + savingsAccount.getBalance());
        ((SavingsAccount) savingsAccount).addInterest();
        System.out.println("Savings account balance after 5% interest: " + savingsAccount.getBalance());

        System.out.println("Attempting to withdraw 10000 from savings (balance: " + savingsAccount.getBalance() + ")");
        double savingsWithdrawn = savingsAccount.withdraw(10000);
        System.out.println("Amount withdrawn: " + savingsWithdrawn);
        System.out.println("Savings balance after attempt: " + savingsAccount.getBalance());

        System.out.println("Testing Check Numbers");
        CheckingAccount ca = (CheckingAccount) checkingAccount;
        System.out.println("Check number 1: " + ca.getNextCheckNumber());
        System.out.println("Check number 2: " + ca.getNextCheckNumber());
        System.out.println("Check number 3: " + ca.getNextCheckNumber());

        System.out.println("Testing Charge All Accounts");
        companyChecking.deposit(1000);
        companySavings.deposit(1000);
        System.out.println("Company checking balance before fee: " + companyChecking.getBalance());
        System.out.println("Company savings balance before fee: " + companySavings.getBalance());

        company1.chargeAllAccounts(50);
        System.out.println("After charging 50 (doubled for savings): ");
        System.out.println("Company checking balance after fee: " + companyChecking.getBalance());
        System.out.println("Company savings balance after fee: " + companySavings.getBalance());

        // Test Person charge all accounts
        System.out.println("Person's checking balance before fee: " + checkingAccount.getBalance());
        System.out.println("Person's savings balance before fee: " + savingsAccount.getBalance());
        person1.chargeAllAccounts(25);
        System.out.println("After charging 25 to person:");
        System.out.println("Person's checking balance after fee: " + checkingAccount.getBalance());
        System.out.println("Person's savings balance after fee: " + savingsAccount.getBalance());

        System.out.println("Testing Remove Account");
        System.out.println("Total accounts before removal: " + controller.getAccounts().size());
        System.out.println("Person's accounts before removal: " + person1.getAccounts().size());
        controller.removeAccount(checkingAccount);
        System.out.println("Total accounts after removal: " + controller.getAccounts().size());
        System.out.println("Person's accounts after removal: " + person1.getAccounts().size());

        System.out.println("Testing Remove Customer");
        System.out.println("Total customers before removal: " + controller.getCustomers().size());
        System.out.println("Total accounts before customer removal: " + controller.getAccounts().size());
        controller.removeCustomer(company1);
        System.out.println("Total customers after removal: " + controller.getCustomers().size());
        System.out.println("Total accounts after customer removal: " + controller.getAccounts().size());
    }
}
