package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Floor {
    private final int floorNumber;
    private final List<PassengerGroup> waitingPassengers = new ArrayList<>();

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public synchronized void addWaitingPassengers(PassengerGroup group) {
        waitingPassengers.add(group);
    }

    public synchronized boolean hasWaitingPassengers() {
        return !waitingPassengers.isEmpty();
    }

    public synchronized List<PassengerGroup> removeUpPassengers() {
        List<PassengerGroup> up = new ArrayList<>();
        Iterator<PassengerGroup> it = waitingPassengers.iterator();
        while (it.hasNext()) {
            PassengerGroup pg = it.next();
            if (pg.isGoingUp()) {
                up.add(pg);
                it.remove();
            }
        }
        return up;
    }

    public synchronized List<PassengerGroup> removeDownPassengers() {
        List<PassengerGroup> down = new ArrayList<>();
        Iterator<PassengerGroup> it = waitingPassengers.iterator();
        while (it.hasNext()) {
            PassengerGroup pg = it.next();
            if (pg.isGoingDown()) {
                down.add(pg);
                it.remove();
            }
        }
        return down;
    }
}
