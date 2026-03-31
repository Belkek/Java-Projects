package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.model;

public class SmallSafetyDepositBox extends SafetyDepositBox {

    private double capacity;

    public SmallSafetyDepositBox() {
        super();
    }

    public SmallSafetyDepositBox(double id, double capacity) {
        super(id);
        this.capacity = capacity;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }
}
