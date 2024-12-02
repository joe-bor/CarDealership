package com.pluralsight;

import com.pluralsight.model.Vehicle;
import com.pluralsight.model.contract.SalesContract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalesContractTest {

    Vehicle vehicle;
    SalesContract salesContract;

    @BeforeEach
    void initVehicle() {
        vehicle = new Vehicle(123, 2024, "Ford", "Focus", "Sedan", "black", 24000, 995);

         salesContract = new SalesContract("Today", "Joezari", "joe@email", vehicle, 5, 100, 295, true);
    }


    @Test
    public void testGetTotalPrice() {

        assertEquals(1439.75, salesContract.getTotalPrice());
    }

    @Test
    public void testGetMonthlyPayment() {
        double monthlyPayment = salesContract.getMonthlyPayment();

        assertEquals(63.325, monthlyPayment, 0.001);
    }
}