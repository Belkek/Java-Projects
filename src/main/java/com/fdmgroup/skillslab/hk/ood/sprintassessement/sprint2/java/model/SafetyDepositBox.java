package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.model;

public abstract class SafetyDepositBox {

    private boolean isAllotted;
    private double id;

    public SafetyDepositBox() {
        this.isAllotted = false;
    }

    public SafetyDepositBox(double id) {
        this.id = id;
        this.isAllotted = false;
    }

    public boolean isAllotted() {
        return isAllotted;
    }

    public void setAllotted(boolean allotted) {
        isAllotted = allotted;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }
}
