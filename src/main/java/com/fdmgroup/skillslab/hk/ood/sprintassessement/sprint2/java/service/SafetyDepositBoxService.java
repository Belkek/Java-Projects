package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.service;

import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.model.SafetyDepositBox;
import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.model.SmallSafetyDepositBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SafetyDepositBoxService {

    private static SafetyDepositBoxService safetyDepositBoxService;
    private List<SafetyDepositBox> safetyDepositBoxes;
    private int numberOfSafetyDepositBox;

    // Flag for testing purposes to indicate waiting threads
    private volatile boolean isWaiting;

    private SafetyDepositBoxService() {
        this.safetyDepositBoxes = new ArrayList<>();
        this.numberOfSafetyDepositBox = 2;
        this.isWaiting = false;
    }

    public static synchronized SafetyDepositBoxService getInstance() {
        if (safetyDepositBoxService == null) {
            safetyDepositBoxService = new SafetyDepositBoxService();
        }
        return safetyDepositBoxService;
    }

    public static synchronized void resetInstance() {
        safetyDepositBoxService = null;
    }

    public void setNumberOfSafetyDepositBoxes(int numberOfSafetyDepositBox) {
        this.numberOfSafetyDepositBox = numberOfSafetyDepositBox;
    }

    public synchronized int getNumberOfSafetyDepositBoxes() {
        return numberOfSafetyDepositBox;
    }

    public synchronized SafetyDepositBox allocateSafetyDepositBox() {
        Optional<SafetyDepositBox> availableBox = getReleasedSafetyDepositBox();

        if (availableBox.isPresent()) {
            SafetyDepositBox box = availableBox.get();
            box.setAllotted(true);
            return box;
        }

        if (safetyDepositBoxes.size() < numberOfSafetyDepositBox) {
            SafetyDepositBox newBox = new SmallSafetyDepositBox(
                    safetyDepositBoxes.size() + 1,
                    100.0
            );
            newBox.setAllotted(true);
            safetyDepositBoxes.add(newBox);
            return newBox;
        }

        while (true) {
            isWaiting = true;
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted while waiting for safety deposit box", e);
            }

            Optional<SafetyDepositBox> releasedBox = getReleasedSafetyDepositBox();
            if (releasedBox.isPresent()) {
                isWaiting = false;
                SafetyDepositBox box = releasedBox.get();
                box.setAllotted(true);
                return box;
            }
        }
    }

    public synchronized int getNumberOfAvailableSafetyDepositBoxes() {
        int count = 0;
        for (SafetyDepositBox box : safetyDepositBoxes) {
            if (!box.isAllotted()) {
                count++;
            }
        }
        return count;
    }

    public synchronized Optional<SafetyDepositBox> getReleasedSafetyDepositBox() {
        for (SafetyDepositBox box : safetyDepositBoxes) {
            if (!box.isAllotted()) {
                return Optional.of(box);
            }
        }
        return Optional.empty();
    }

    public synchronized void releaseSafetyDepositBox(SafetyDepositBox safetyDepositBox) {
        if (safetyDepositBox != null && safetyDepositBoxes.contains(safetyDepositBox)) {
            safetyDepositBox.setAllotted(false);
            notifyAll();
        }
    }

    public synchronized List<SafetyDepositBox> getSafetyDepositBoxes() {
        return new ArrayList<>(safetyDepositBoxes);
    }

    public synchronized void clearAllBoxes() {
        safetyDepositBoxes.clear();
        isWaiting = false;
    }
}