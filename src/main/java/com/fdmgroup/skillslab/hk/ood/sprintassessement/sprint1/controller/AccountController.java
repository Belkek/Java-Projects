package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.controller;

import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model.*;

import java.util.ArrayList;
import java.util.List;

public class AccountController {
    private List<Customer> customers;
    private List<Account> accounts;

    public AccountController() {
        this.customers = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Customer createCustomer(String name, String address, String type) {
        Customer customer;

        if (type.equalsIgnoreCase("person")) {
            customer = new Person(name, address);
        } else if (type.equalsIgnoreCase("company")) {
            customer = new Company(name, address);
        } else {
            return null;
        }

        customers.add(customer);
        return customer;
    }

    public void removeCustomer(Customer customer) {
        for (Account account : customer.getAccounts()) {
            accounts.remove(account);
        }
        customers.remove(customer);
    }

    public Account createAccount(Customer customer, String type) {
        Account account;

        if (type.equalsIgnoreCase("checking")) {
            account = new CheckingAccount();
        } else if (type.equalsIgnoreCase("savings")) {
            account = new SavingsAccount();
        } else {
            return null;
        }

        accounts.add(account);
        customer.addAccount(account);

        return account;
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        for (Customer customer : customers) {
            customer.removeAccount(account);
        }
    }
}
