package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model;

public abstract class Account {
    private final long ACCOUNT_ID;
    private static long nextAccountId = 1000;
    protected double balance;

    public Account() {
        this.ACCOUNT_ID = nextAccountId;
        nextAccountId += 5;
        this.balance = 0.0;
    }

    public long getACCOUNT_ID() {
        return ACCOUNT_ID;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public double withdraw(double amount) {
        this.balance -= amount;
        return amount;
    }

    public void correctBalance(double amount) {
        this.balance = amount;
    }
}
