package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.service;

public class FeeCalculatorServiceImpl implements FeeCalculatorService {

    @Override
    public double calculateFee(double balance) {
        if (balance <= 100) {
            return 20.00;
        } else if (balance <= 500) {
            return 15.00;
        } else if (balance <= 1000) {
            return 10.00;
        } else if (balance <= 2000) {
            return 5.00;
        } else {
            return 0.00;
        }
    }
}