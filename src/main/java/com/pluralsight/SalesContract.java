package com.pluralsight;

public class SalesContract extends Contract {

    private double salesTax;
    private double recordingFee;
    private double processingFee;
    boolean financed;
    private double monthlyPayment;

    public SalesContract(String contractDate, String customerName, String customerEmail, Vehicle vehicleSold, double salesTax, double recordingFee, double processingFee, boolean financed) {
        super(contractDate, customerName, customerEmail, vehicleSold);
        this.salesTax = salesTax;
        this.recordingFee = recordingFee;
        this.processingFee = processingFee;
        this.financed = financed;
    }

    public double getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }

    public double getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(double recordingFee) {
        this.recordingFee = recordingFee;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    public boolean isFinanced() {
        return financed;
    }

    public void setFinanced(boolean financed) {
        this.financed = financed;
    }

    @Override
    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    @Override
    public double getTotalPrice() {
        double totalPrice = 0;
        double vehiclePrice = this.getVehicleSold().getPrice();

        totalPrice += vehiclePrice;
        totalPrice += totalPrice * this.getSalesTax() / 100;
        totalPrice += this.getRecordingFee();
        totalPrice += this.getVehicleSold().getPrice() < 10000 ? 295 : 495; // processing fee

        return totalPrice;
    }

    @Override
    public double getMonthlyPayment() {
        double totalPrice = this.getTotalPrice();
        double interestRate = totalPrice > 10000 ? 4.25 : 5.25;
        double monthlyInterest = interestRate / 12 / 100;
        double loanTerm = totalPrice > 10000 ? 48 : 24;

        if (this.isFinanced()) {
            return totalPrice * monthlyInterest / (1 - Math.pow(1 + monthlyInterest, -loanTerm));

        } else {
            return 0;
        }

    }
}
