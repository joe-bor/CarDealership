package com.pluralsight;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeaseContractTest {

    Vehicle vehicle;
    LeaseContract leaseContract;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle(37846, 2021, "Chevrolet", "Silverado", "truck", "Black", 2750, 31995.00);

        leaseContract = new LeaseContract("20210928", "Zachary Westly", "zach@texas.com", vehicle, 15997.50, 7.0);

    }

    @Test
    void getTotalPrice() {
        assertEquals(19490.04, leaseContract.getTotalPrice());

        /*
        org.opentest4j.AssertionFailedError:
        Expected :19490.04
        Actual   :19383.600354031718
        * */

    }

    @Test
    void setMonthlyPayment() {
        double monthlyPayment = leaseContract.getMonthlyPayment();

        assertEquals(541.39, monthlyPayment);

        /**
         * org.opentest4j.AssertionFailedError:
         * Expected :541.39
         * Actual   :538.4333431675477
         */
    }
}