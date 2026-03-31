package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model;

public class Person extends Customer{
    public Person(String name, String address) {
        super(name, address);
    }

    @Override
    public void chargeAllAccounts(double amount) {
        for (Account account : getAccounts()) {
            account.withdraw(amount);
        }
    }
}
