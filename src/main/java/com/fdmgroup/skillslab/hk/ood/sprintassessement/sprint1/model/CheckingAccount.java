package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint1.model;

public class CheckingAccount extends Account{
    private int nextCheckNumber;

    public CheckingAccount() {
        super();
        this.nextCheckNumber = 1;
    }

    public int getNextCheckNumber() {
        int currentCheckNumber = nextCheckNumber;
        nextCheckNumber++;
        return currentCheckNumber;
    }
}
