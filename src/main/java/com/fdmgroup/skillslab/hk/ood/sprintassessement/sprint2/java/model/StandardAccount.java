package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.model;

import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model.Account;

public class StandardAccount extends Account {

    private String accountHolderName;

    public StandardAccount(String accountHolderName) {
        super();
        this.accountHolderName = accountHolderName;
    }

    public StandardAccount(String accountHolderName, double initialBalance) {
        super();
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }
}