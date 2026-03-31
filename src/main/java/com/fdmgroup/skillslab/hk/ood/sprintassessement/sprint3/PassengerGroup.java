package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint3;

public class PassengerGroup {
    private final int originFloor;
    private final int destinationFloor;
    private final int numberOfPassengers;

    public PassengerGroup(int originFloor, int destinationFloor, int numberOfPassengers) {
        this.originFloor = originFloor;
        this.destinationFloor = destinationFloor;
        this.numberOfPassengers = numberOfPassengers;
    }

    public int getOriginFloor() {
        return originFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public boolean isGoingUp() {
        return destinationFloor > originFloor;
    }

    public boolean isGoingDown() {
        return destinationFloor < originFloor;
    }
}
