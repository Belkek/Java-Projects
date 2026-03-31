package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint3;

import java.util.ArrayList;
import java.util.List;

public class Elevator implements Runnable {
    private final int id;
    private int currentFloor = 0;
    private final List<PassengerGroup> onBoard = new ArrayList<>();
    private final Building building;
    private int totalDelivered = 0;

    public Elevator(int id, Building building) {
        this.id = id;
        this.building = building;
    }

    @Override
    public void run() {
        log("Starting on floor G");
        while (building.isRunning()) {
            if (!onBoard.isEmpty()) {
                // Find the closest destination among all on-board passengers
                int closestDest = findClosestDestination();
                moveOneFloorTowards(closestDest);
                unloadAtCurrentFloor();
            } else {
                seekAndLoadPassengers();
            }
        }

        log("Shutting down on floor " + floorStr(currentFloor)
                + ". Total passengers delivered: " + totalDelivered);
    }

    private void seekAndLoadPassengers() {
        int targetFloor = findAndClaimFloor();
        if (targetFloor != -1) {
            log("Heading to floor " + floorStr(targetFloor) + " for pickup");
            moveToFloor(targetFloor);
            if (building.isRunning()) {
                loadPassengers();
            }
            building.releaseFloor(targetFloor);
        } else {
            // No unclaimed floor with passengers found — wait and retry
            simulateSleep(1);
        }
    }

    private int findAndClaimFloor() {
         synchronized (building) {
            int bestFloor = -1;
            int bestDist = Integer.MAX_VALUE;
            for (int i = 0; i < building.getNumFloors(); i++) {
                if (building.getFloor(i).hasWaitingPassengers() && !building.isFloorClaimed(i)) {
                    int dist = Math.abs(currentFloor - i);
                    if (dist < bestDist) {
                        bestDist = dist;
                        bestFloor = i;
                    }
                }
            }
            if (bestFloor != -1) {
                building.claimFloor(bestFloor);
            }
            return bestFloor;
        }
    }

    private void loadPassengers() {
        Floor floor = building.getFloor(currentFloor);

        // Try UP passengers first
        List<PassengerGroup> upPassengers = floor.removeUpPassengers();
        if (!upPassengers.isEmpty()) {
            onBoard.addAll(upPassengers);
            int count = upPassengers.stream().mapToInt(PassengerGroup::getNumberOfPassengers).sum();
            log("Loaded " + count + " UP passenger(s) at floor " + floorStr(currentFloor)
                    + " " + formatDestinations(upPassengers));
            simulateSleep(5);
            return;
        }

        // No up passengers — try down passengers
        List<PassengerGroup> downPassengers = floor.removeDownPassengers();
        if (!downPassengers.isEmpty()) {
            onBoard.addAll(downPassengers);
            int count = downPassengers.stream().mapToInt(PassengerGroup::getNumberOfPassengers).sum();
            log("Loaded " + count + " DOWN passenger(s) at floor " + floorStr(currentFloor)
                    + " " + formatDestinations(downPassengers));
            simulateSleep(5);
            return;
        }

        log("Arrived at floor " + floorStr(currentFloor) + " but no passengers to load");
    }

    private void unloadAtCurrentFloor() {
        List<PassengerGroup> toRemove = new ArrayList<>();
        for (PassengerGroup pg : onBoard) {
            if (pg.getDestinationFloor() == currentFloor) {
                toRemove.add(pg);
            }
        }
        if (!toRemove.isEmpty()) {
            int count = toRemove.stream().mapToInt(PassengerGroup::getNumberOfPassengers).sum();
            onBoard.removeAll(toRemove);
            totalDelivered += count;
            log("Unloaded " + count + " passenger(s) at floor " + floorStr(currentFloor)
                    + " | Remaining on board: " + getTotalOnBoard());
            simulateSleep(5);
        }
    }

    private int findClosestDestination() {
        int closest = onBoard.get(0).getDestinationFloor();
        int minDist = Math.abs(currentFloor - closest);
        for (int i = 1; i < onBoard.size(); i++) {
            int dest = onBoard.get(i).getDestinationFloor();
            int dist = Math.abs(currentFloor - dest);
            if (dist < minDist) {
                minDist = dist;
                closest = dest;
            }
        }
        return closest;
    }

    private void moveToFloor(int target) {
        while (currentFloor != target && building.isRunning()) {
            moveOneFloorTowards(target);
        }
    }

    private void moveOneFloorTowards(int target) {
        if (currentFloor == target) return;

        simulateSleep(3);
        if (!building.isRunning()) return;

        if (currentFloor < target) {
            currentFloor++;
        } else {
            currentFloor--;
        }
        log("Now at floor " + floorStr(currentFloor));
    }

    private int getTotalOnBoard() {
        return onBoard.stream().mapToInt(PassengerGroup::getNumberOfPassengers).sum();
    }

    private void simulateSleep(int simulatedSeconds) {
        try {
            Thread.sleep((long) simulatedSeconds * building.getRate());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String floorStr(int floor) {
        return floor == 0 ? "G" : String.valueOf(floor);
    }

    private String formatDestinations(List<PassengerGroup> groups) {
        StringBuilder sb = new StringBuilder("-> destinations: [");
        for (int i = 0; i < groups.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(floorStr(groups.get(i).getDestinationFloor()));
        }
        sb.append("]");
        return sb.toString();
    }

    private void log(String message) {
        System.out.println("[Elevator " + id + "] " + message);
    }
}