package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static final int NUM_ELEVATORS = 2;

    static void main(String[] args) {

        try {
            // TODO To provide a headstart, below code helps reading out a ElevatorConfig.txt file
            var fileLines =  readElevatorConfigFile("sprint3.assessment.ElevatorConfig.txt");
            for(var line : fileLines) {
                System.out.println(line);
            }

            // Remove empty lines before processing
            var cleanedLines = new ArrayList<String>();
            for (var line : fileLines) {
                if (!line.trim().isEmpty()) {
                    cleanedLines.add(line.trim());
                }
            }

            // ---- Parse config ----
            int totalTime = Integer.parseInt(cleanedLines.get(0));
            int rate = Integer.parseInt(cleanedLines.get(1));

            // Parse passenger requests & determine max floor
            int maxFloor = 0;
            var passengerData = new ArrayList<String[]>();
            for (int i = 2; i < cleanedLines.size(); i++) {
                String entry = cleanedLines.get(i);
                String[] parts = entry.split("\\s+");
                passengerData.add(parts);
                maxFloor = Math.max(maxFloor, parseFloor(parts[0]));
                maxFloor = Math.max(maxFloor, parseFloor(parts[1]));
            }

            // Build the building and populate floors
            Building building = new Building(maxFloor + 1, totalTime, rate);

            int totalPassengers = 0;
            for (String[] parts : passengerData) {
                int currentFloor = parseFloor(parts[0]);
                int destFloor = parseFloor(parts[1]);
                int numPassengers = Integer.parseInt(parts[2]);
                totalPassengers += numPassengers;
                PassengerGroup group = new PassengerGroup(currentFloor, destFloor, numPassengers);
                building.getFloor(currentFloor).addWaitingPassengers(group);
            }

            // Start elevator threads
            System.out.println("\nSIMULATION START (Total passengers: " + totalPassengers + ")\n");

            var threads = new ArrayList<Thread>();
            for (int i = 0; i < NUM_ELEVATORS; i++) {
                Elevator elevator = new Elevator(i + 1, building);
                Thread thread = new Thread(elevator, "Elevator-" + (i + 1));
                threads.add(thread);
                thread.start();
            }

            // Wait for simulation duration
//            try {
//                Thread.sleep((long) totalTime * rate);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }

           //
            System.out.println("\nSIMULATION TIME ELAPSED\n");

            // Wait for elevator threads to finish
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            building.stopSimulation();

            System.out.println("\nSIMULATION COMPLETE");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int parseFloor(String floor) {
        if (floor.equalsIgnoreCase("G")) return 0;
        return Integer.parseInt(floor);
    }

    static List<String> readElevatorConfigFile(String elevatorConfigFile) throws IOException {

        var line = "";
        var lines = new ArrayList<String>();
        try(
                var inputStream = Main.class.getClassLoader().getResourceAsStream("sprint3.assessment.ElevatorConfig.txt");
                var bufferedReader = new BufferedReader(new InputStreamReader(inputStream))
        ){
            while((line = bufferedReader.readLine()) != null)
                lines.add(line);
            return lines;
        } catch (IOException e) {
            throw e;
        }
    }
}