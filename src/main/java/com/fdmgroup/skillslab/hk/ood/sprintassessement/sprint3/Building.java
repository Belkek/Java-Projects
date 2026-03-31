package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint3;

import java.util.HashSet;
import java.util.Set;

public class Building {
    private final Floor[] floors;
    private final int totalTime;
    private final int rate;
    private volatile boolean running = true;
    private final Set<Integer> claimedFloors = new HashSet<>();

    public Building(int numFloors, int totalTime, int rate) {
        this.floors = new Floor[numFloors];
        for (int i = 0; i < numFloors; i++) {
            floors[i] = new Floor(i);
        }
        this.totalTime = totalTime;
        this.rate = rate;
    }

    public Floor getFloor(int floorNumber) {
        return floors[floorNumber];
    }

    public int getNumFloors() {
        return floors.length;
    }

    public int getRate() {
        return rate;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public boolean isRunning() {
        return running;
    }

    public void stopSimulation() {
        running = false;
    }

    public synchronized boolean claimFloor(int floorNumber) {
        if (claimedFloors.contains(floorNumber)) {
            return false;
        }
        claimedFloors.add(floorNumber);
        return true;
    }

    public synchronized void releaseFloor(int floorNumber) {
        claimedFloors.remove(floorNumber);
    }

    public synchronized boolean isFloorClaimed(int floorNumber) {
        return claimedFloors.contains(floorNumber);
    }
}
