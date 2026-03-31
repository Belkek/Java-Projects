package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Customer {
    private final long CUSTOMER_ID;
    private static long nextCustomerId = 2_000_000;
    private String name;
    private String address;
    private List<Account> accounts;

    public Customer(String name, String address) {
        this.CUSTOMER_ID = nextCustomerId;
        nextCustomerId += 7;
        this.name = name;
        this.address = address;
        this.accounts = new ArrayList<>();
    }

    public long getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public void removeAccount(Account account) {
        this.accounts.remove(account);
    }

    public abstract void chargeAllAccounts(double amount);
}
