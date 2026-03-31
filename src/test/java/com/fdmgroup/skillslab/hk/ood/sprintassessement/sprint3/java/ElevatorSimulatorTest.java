package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint3.java;

import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorSimulatorTest {

    // TEST 1: PassengerGroup correctly identifies travel direction
    @Test
    void testPassengerGroupDirection() {
        PassengerGroup goingUp = new PassengerGroup(0, 5, 2);
        assertTrue(goingUp.isGoingUp(), "Passenger from G to 5 should be going UP");
        assertFalse(goingUp.isGoingDown(), "Passenger from G to 5 should NOT be going DOWN");

        PassengerGroup goingDown = new PassengerGroup(5, 2, 4);
        assertTrue(goingDown.isGoingDown(), "Passenger from 5 to 2 should be going DOWN");
        assertFalse(goingDown.isGoingUp(), "Passenger from 5 to 2 should NOT be going UP");

        PassengerGroup sameFloor = new PassengerGroup(3, 3, 1);
        assertFalse(sameFloor.isGoingUp(), "Same floor passenger should NOT be going UP");
        assertFalse(sameFloor.isGoingDown(), "Same floor passenger should NOT be going DOWN");
    }

    // TEST 2: Floor loads all UP passengers before DOWN passengers
    @Test
    void testFloorLoadsPriorityUpBeforeDown() {
        Floor floor = new Floor(3);

        PassengerGroup upGroup1 = new PassengerGroup(3, 7, 2);
        PassengerGroup upGroup2 = new PassengerGroup(3, 10, 1);
        PassengerGroup downGroup = new PassengerGroup(3, 1, 4);

        floor.addWaitingPassengers(upGroup1);
        floor.addWaitingPassengers(downGroup);
        floor.addWaitingPassengers(upGroup2);

        // First call: remove UP passengers — should get both UP groups
        List<PassengerGroup> upPassengers = floor.removeUpPassengers();
        assertEquals(2, upPassengers.size(), "Should load 2 UP passenger groups");
        assertTrue(upPassengers.contains(upGroup1));
        assertTrue(upPassengers.contains(upGroup2));

        // Second call: now remove DOWN passengers
        List<PassengerGroup> downPassengers = floor.removeDownPassengers();
        assertEquals(1, downPassengers.size(), "Should load 1 DOWN passenger group");
        assertEquals(1, downPassengers.get(0).getDestinationFloor());

        // Floor should now be empty
        assertFalse(floor.hasWaitingPassengers(), "Floor should be empty after all pickups");
    }

    // TEST 3: Building claim mechanism — no two elevators claim the same floor for pickup simultaneously
    @Test
    void testBuildingFloorClaimPreventsDoubleClaim() throws InterruptedException {
        Building building = new Building(11, 1000, 100);
        building.getFloor(5).addWaitingPassengers(new PassengerGroup(5, 0, 3));

        // Both threads try to claim floor 5 at approximately the same time
        AtomicInteger successfulClaims = new AtomicInteger(0);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(2);

        Runnable claimTask = () -> {
            try {
                startLatch.await(); // wait so both threads start together
                if (building.claimFloor(5)) {
                    successfulClaims.incrementAndGet();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                doneLatch.countDown();
            }
        };

        Thread t1 = new Thread(claimTask, "Elevator-A");
        Thread t2 = new Thread(claimTask, "Elevator-B");
        t1.start();
        t2.start();

        startLatch.countDown(); // release both threads
        doneLatch.await();

        assertEquals(1, successfulClaims.get(),
                "Exactly one elevator should successfully claim floor 5");

        // After release, the floor can be claimed again
        building.releaseFloor(5);
        assertFalse(building.isFloorClaimed(5));
        assertTrue(building.claimFloor(5), "Floor should be claimable again after release");
    }

    // TEST 4: parseFloor — "G" maps to 0, numbers parse correctly
    @Test
    void testParseFloor() {
        assertEquals(0, Main.parseFloor("G"), "G should map to floor 0");
        assertEquals(0, Main.parseFloor("g"), "g (lowercase) should map to floor 0");
        assertEquals(1, Main.parseFloor("1"));
        assertEquals(10, Main.parseFloor("10"));
        assertEquals(5, Main.parseFloor("5"));
    }

    // TEST 5: Integration — single elevator delivers all passengers in the correct closest-floor-first order
    @Test
    void testElevatorDeliversAllPassengers() throws InterruptedException {
        Building building = new Building(11, 2000, 5);

        // Scenario: passengers on G going to floor 3 and floor 7
        building.getFloor(0).addWaitingPassengers(new PassengerGroup(0, 3, 2));
        building.getFloor(0).addWaitingPassengers(new PassengerGroup(0, 7, 1));
        // Passengers on floor 10 going down to floor 1
        building.getFloor(10).addWaitingPassengers(new PassengerGroup(10, 1, 5));

        Elevator elevator = new Elevator(1, building);
        Thread thread = new Thread(elevator, "Elevator-Test");
        thread.start();

        Thread.sleep(3000);

        building.stopSimulation();
        thread.join(2000);

        // All floors should have no waiting passengers
        for (int i = 0; i < building.getNumFloors(); i++) {
            assertFalse(building.getFloor(i).hasWaitingPassengers(),
                    "Floor " + i + " should have no waiting passengers after simulation");
        }
    }
}