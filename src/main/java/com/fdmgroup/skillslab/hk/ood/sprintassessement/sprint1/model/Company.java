package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model;

public class Company extends Customer{
    public Company(String name, String address) {
        super(name, address);
    }

    @Override
    public void chargeAllAccounts(double amount) {
        for (Account account : getAccounts()) {
            if (account instanceof CheckingAccount) {
                account.withdraw(amount);
            } else if (account instanceof SavingsAccount) {
                account.withdraw(amount * 2);
            }
        }
    }
}
