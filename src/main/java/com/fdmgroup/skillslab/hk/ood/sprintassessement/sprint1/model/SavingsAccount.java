package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model;

public class SavingsAccount extends Account{
    private double interestRate;

    public SavingsAccount() {
        super();
        this.interestRate = 0.0;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double rate) {
        this.interestRate = rate;
    }

    @Override
    public double withdraw(double amount) {
        if (amount > getBalance()) {
            return 0;
        }
        balance -= amount;
        return amount;
    }

    public void addInterest() {
        double interestDue = getBalance() * interestRate / 100;
        deposit(interestDue);
    }
}
