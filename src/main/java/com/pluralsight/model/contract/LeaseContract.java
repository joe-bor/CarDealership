package com.pluralsight.model.contract;

import com.pluralsight.model.Vehicle;

public class LeaseContract extends Contract {

    private double expectedEndingValue;
    private double leaseFee;
    private double monthlyPayment;
    private final double LEASE_TERM = 36.0;
    private final double LEASE_FINANCE_RATE = 4.0;

    public LeaseContract(String contractDate, String customerName, String customerEmail, Vehicle vehicleSold, double expectedEndingValue, double leaseFee) {
        super(contractDate, customerName, customerEmail, vehicleSold);
        this.expectedEndingValue = expectedEndingValue;
        this.leaseFee = leaseFee;
    }

    public double getExpectedEndingValue() {
        return expectedEndingValue;
    }

    public void setExpectedEndingValue(double expectedEndingValue) {
        this.expectedEndingValue = expectedEndingValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }

    public void setLeaseFee(double leaseFee) {
        this.leaseFee = leaseFee;
    }

    @Override
    public double getTotalPrice() {
        return this.getMonthlyPayment() * LEASE_TERM;
    }

    @Override
    public double getMonthlyPayment() {
        double carPrice = this.getVehicleSold().getPrice();
        double leaseFee = carPrice * (this.getLeaseFee() / 100); // % of the original car price
        double loanAmount = carPrice - this.getExpectedEndingValue() + leaseFee;
        double monthlyInterestRate = LEASE_FINANCE_RATE / 12 / 100;

//        System.out.println(carPrice);
//        System.out.println(leaseFee);
//        System.out.println(loanAmount);
//        System.out.println(monthlyInterestRate);

        return loanAmount * monthlyInterestRate / (1 - Math.pow(1 + monthlyInterestRate, -LEASE_TERM));
    }

    @Override
    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }
}
