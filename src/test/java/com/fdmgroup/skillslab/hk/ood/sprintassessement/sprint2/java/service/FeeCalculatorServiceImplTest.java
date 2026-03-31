package com.fdmgroup.skillslab.hk.ood.sprintassessement.sprint2.java.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeeCalculatorServiceImplTest {

    private FeeCalculatorService feeCalculatorService;

    @BeforeEach
    void setUp() {
        feeCalculatorService = new FeeCalculatorServiceImpl();
    }

    @Test
    @DisplayName("Should return $20.00 fee when balance is less than $100")
    void calculateFee_WhenBalanceIsLessThan100_ShouldReturn20() {
        // Arrange
        double balance = 50.00;
        double expectedFee = 20.00;

        // Act
        double actualFee = feeCalculatorService.calculateFee(balance);

        // Assert
        assertEquals(expectedFee, actualFee);
    }

    @Test
    @DisplayName("Should return $20.00 fee when balance is exactly $100")
    void calculateFee_WhenBalanceIsExactly100_ShouldReturn20() {
        // Arrange
        double balance = 100.00;
        double expectedFee = 20.00;

        // Act
        double actualFee = feeCalculatorService.calculateFee(balance);

        // Assert
        assertEquals(expectedFee, actualFee);
    }

    @Test
    @DisplayName("Should return $15.00 fee when balance is greater than $100 but less than or equal to $500")
    void calculateFee_WhenBalanceIsBetween100And500_ShouldReturn15() {
        // Arrange
        double balance = 300.00;
        double expectedFee = 15.00;

        // Act
        double actualFee = feeCalculatorService.calculateFee(balance);

        // Assert
        assertEquals(expectedFee, actualFee);
    }

    @Test
    @DisplayName("Should return $15.00 fee when balance is exactly $500")
    void calculateFee_WhenBalanceIsExactly500_ShouldReturn15() {
        // Arrange
        double balance = 500.00;
        double expectedFee = 15.00;

        // Act
        double actualFee = feeCalculatorService.calculateFee(balance);

        // Assert
        assertEquals(expectedFee, actualFee);
    }

    @Test
    @DisplayName("Should return $10.00 fee when balance is greater than $500 but less than or equal to $1000")
    void calculateFee_WhenBalanceIsBetween500And1000_ShouldReturn10() {
        // Arrange
        double balance = 750.00;
        double expectedFee = 10.00;

        // Act
        double actualFee = feeCalculatorService.calculateFee(balance);

        // Assert
        assertEquals(expectedFee, actualFee);
    }

    @Test
    @DisplayName("Should return $10.00 fee when balance is exactly $1000")
    void calculateFee_WhenBalanceIsExactly1000_ShouldReturn10() {
        // Arrange
        double balance = 1000.00;
        double expectedFee = 10.00;

        // Act
        double actualFee = feeCalculatorService.calculateFee(balance);

        // Assert
        assertEquals(expectedFee, actualFee, 0.001);
    }

    @Test
    @DisplayName("Should return $5.00 fee when balance is greater than $1000 but less than or equal to $2000")
    void calculateFee_WhenBalanceIsBetween1000And2000_ShouldReturn5() {
        // Arrange
        double balance = 1500.00;
        double expectedFee = 5.00;

        // Act
        double actualFee = feeCalculatorService.calculateFee(balance);

        // Assert
        assertEquals(expectedFee, actualFee);
    }

    @Test
    @DisplayName("Should return $5.00 fee when balance is exactly $2000")
    void calculateFee_WhenBalanceIsExactly2000_ShouldReturn5() {
        // Arrange
        double balance = 2000.00;
        double expectedFee = 5.00;

        // Act
        double actualFee = feeCalculatorService.calculateFee(balance);

        // Assert
        assertEquals(expectedFee, actualFee);
    }

    @Test
    @DisplayName("Should return $0.00 fee when balance is greater than $2000")
    void calculateFee_WhenBalanceIsGreaterThan2000_ShouldReturn0() {
        // Arrange
        double balance = 5000.00;
        double expectedFee = 0.00;

        // Act
        double actualFee = feeCalculatorService.calculateFee(balance);

        // Assert
        assertEquals(expectedFee, actualFee);
    }

    @Test
    @DisplayName("Should return $20.00 fee when balance is zero")
    void calculateFee_WhenBalanceIsZero_ShouldReturn20() {
        // Arrange
        double balance = 0.00;
        double expectedFee = 20.00;

        // Act
        double actualFee = feeCalculatorService.calculateFee(balance);

        // Assert
        assertEquals(expectedFee, actualFee);
    }

    @Test
    @DisplayName("Should return $20.00 fee when balance is negative")
    void calculateFee_WhenBalanceIsNegative_ShouldReturn20() {
        // Arrange
        double balance = -50.00;
        double expectedFee = 20.00;

        // Act
        double actualFee = feeCalculatorService.calculateFee(balance);

        // Assert
        assertEquals(expectedFee, actualFee);
    }
}