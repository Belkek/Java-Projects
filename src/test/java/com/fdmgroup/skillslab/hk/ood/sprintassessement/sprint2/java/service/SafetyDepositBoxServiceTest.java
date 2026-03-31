package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.service;

import com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.model.SafetyDepositBox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class SafetyDepositBoxServiceTest {

    private SafetyDepositBoxService service;

    @BeforeEach
    void setUp() {
        SafetyDepositBoxService.resetInstance();
        service = SafetyDepositBoxService.getInstance();
        service.clearAllBoxes();
        service.setNumberOfSafetyDepositBoxes(2);
    }

    @AfterEach
    void tearDown() {
        SafetyDepositBoxService.resetInstance();
    }

    @Test
    @DisplayName("getInstance should always return the same SafetyDepositBoxService instance (Singleton)")
    void getInstance_ShouldAlwaysReturnSameInstance() {
        // Act
        SafetyDepositBoxService instance1 = SafetyDepositBoxService.getInstance();
        SafetyDepositBoxService instance2 = SafetyDepositBoxService.getInstance();

        // Assert
        assertSame(instance1, instance2);
        assertNotNull(instance1);
    }

    @Test
    @DisplayName("allocateSafetyDepositBox should create a new allotted box and reuse released boxes")
    void allocateSafetyDepositBox_ShouldCreateNewBoxAndReuseReleasedBox() {
        // Act -
        SafetyDepositBox box1 = service.allocateSafetyDepositBox();
        SafetyDepositBox box2 = service.allocateSafetyDepositBox();

        // Assert
        assertNotNull(box1);
        assertNotNull(box2);
        assertTrue(box1.isAllotted());
        assertTrue(box2.isAllotted());
        assertEquals(2, service.getSafetyDepositBoxes().size());

        // Release box1 and reallocate - should reuse it
        service.releaseSafetyDepositBox(box1);
        assertFalse(box1.isAllotted());

        SafetyDepositBox reusedBox = service.allocateSafetyDepositBox();
        assertSame(box1, reusedBox);
        assertTrue(reusedBox.isAllotted());
    }

    @Test
    @DisplayName("Two threads requesting two boxes should not result in any thread waiting")
    void twoThreadsRequestingTwoBoxes_ShouldNotResultInAnyThreadWaiting() throws InterruptedException {
        // Arrange
        CountDownLatch allAllocated = new CountDownLatch(2);
        CountDownLatch done = new CountDownLatch(2);

        Runnable task = () -> {
            try {
                SafetyDepositBox box = service.allocateSafetyDepositBox();
                allAllocated.countDown();
                Thread.sleep(5000);
                service.releaseSafetyDepositBox(box);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                done.countDown();
            }
        };

        // Act
        new Thread(task, "Thread-1").start();
        new Thread(task, "Thread-2").start();

        // Assert
        boolean bothAllocatedQuickly = allAllocated.await(2, TimeUnit.SECONDS);
        assertTrue(bothAllocatedQuickly, "No thread should have been kept waiting");

        assertTrue(done.await(15, TimeUnit.SECONDS), "All threads should complete within timeout");
    }

    @Test
    @DisplayName("Three threads requesting two boxes should result in at least one thread waiting")
    void threeThreadsRequestingTwoBoxes_ShouldResultInAtLeastOneThreadWaiting() throws InterruptedException {
        // Arrange
        CountDownLatch allAllocated = new CountDownLatch(3);
        CountDownLatch done = new CountDownLatch(3);

        Runnable task = () -> {
            try {
                SafetyDepositBox box = service.allocateSafetyDepositBox();
                allAllocated.countDown();
                Thread.sleep(5000);
                service.releaseSafetyDepositBox(box);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                done.countDown();
            }
        };

        // Act
        new Thread(task, "Thread-1").start();
        new Thread(task, "Thread-2").start();
        new Thread(task, "Thread-3").start();

        // Assert
        boolean allAllocatedQuickly = allAllocated.await(2, TimeUnit.SECONDS);
        assertFalse(allAllocatedQuickly, "At least one thread should have been kept waiting for a safety deposit box");

        assertTrue(done.await(15, TimeUnit.SECONDS), "All threads should eventually complete");
    }

}