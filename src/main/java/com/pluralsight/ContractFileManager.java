package com.pluralsight;

import com.pluralsight.model.Vehicle;
import com.pluralsight.model.contract.Contract;
import com.pluralsight.model.contract.LeaseContract;
import com.pluralsight.model.contract.SalesContract;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ContractFileManager {

    private final String CONTRACT_FILE_PATH = "contracts.csv";

    public void saveContract(Contract contract) {
        boolean isSalesContract = contract instanceof SalesContract ? true : false;
        Vehicle vehicleInContract = contract.getVehicleSold();

        // build general contract information
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((isSalesContract ? "SALE" : "LEASE") + "|");
        stringBuilder.append(contract.getContractDate() + "|" + contract.getCustomerName() + "|" + contract.getCustomerEmail() + "|" + vehicleInContract.getVin() + "|" + vehicleInContract.getYear() + "|" + vehicleInContract.getMake() + "|" + vehicleInContract.getModel() + "|" + vehicleInContract.getVehicleType() + "|" + vehicleInContract.getColor() + "|" + vehicleInContract.getOdometer() + "|" + String.format("%.2f", vehicleInContract.getPrice()) + "|");

        // append contract properties specific to a subclass
        if (isSalesContract) {
            double salesTax = ((SalesContract) contract).getSalesTax();
            double recordingFee = ((SalesContract) contract).getRecordingFee();
            double processingFee = ((SalesContract) contract).getProcessingFee();
            double totalPrice = contract.getTotalPrice();
            double monthlyPayment = contract.getMonthlyPayment();

            stringBuilder.append(String.format("%.2f", salesTax) + "|" + String.format("%.2f", recordingFee) + "|" + String.format("%.2f", processingFee) + "|" + String.format("%.2f", totalPrice) + "|" + (((SalesContract) contract).isFinanced() ? "YES" : "NO") + "|" + String.format("%.2f", monthlyPayment));
        } else {
            // if it is not a sales contract, then it is a lease contract
            double expectedEndingValue = ((LeaseContract) contract).getExpectedEndingValue();
            double leaseFeeAmount = (((LeaseContract) contract).getLeaseFee() / 100 * vehicleInContract.getPrice());
            double totalPrice = expectedEndingValue + leaseFeeAmount;
            double monthlyPayment = contract.getMonthlyPayment();

            stringBuilder.append(String.format("%.2f", expectedEndingValue) + "|" + String.format("%.2f", leaseFeeAmount) + "|" + String.format("%.2f", totalPrice) + "|" + String.format("%.2f", monthlyPayment));
        }
        stringBuilder.append("\n");

        // log the files to csv
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(CONTRACT_FILE_PATH, true))) {
            bufferedWriter.append(stringBuilder.toString());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Contract> getContract() {
        List<Contract> contractList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CONTRACT_FILE_PATH))) {

            String fileLine;
            while ((fileLine = bufferedReader.readLine()) != null) {
                Contract contract = null;
                String[] lineArr = fileLine.split(Pattern.quote("|"));

                // Parts of generic contract
                String contractType = lineArr[0];
                String contractDate = lineArr[1];
                String name = lineArr[2];
                String email = lineArr[3];

                // Parts of vehicle
                int vin = Integer.parseInt(lineArr[4]);
                int year = Integer.parseInt(lineArr[5]);
                String make = lineArr[6];
                String model = lineArr[7];
                String vehicleType = lineArr[8];
                String color = lineArr[9];
                int odometer = Integer.parseInt(lineArr[10]);
                double price = Double.parseDouble(lineArr[11]);

                Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);

                // Sale contract
                if (contractType.equals("SALE")) {
                    double salesTax = Double.parseDouble(lineArr[12]);
                    double recordingFee = Double.parseDouble(lineArr[13]);
                    double processingFee = Double.parseDouble(lineArr[14]);
                    boolean financed = lineArr[16].equals("NO") ? false : true;
                    double monthlyPayment = Double.parseDouble(lineArr[17]);

                    contract = new SalesContract(contractDate, name, email, vehicle, salesTax, recordingFee, processingFee, financed);
                }

                if (contractType.equals("LEASE")) {
                    double endingValue = Double.parseDouble(lineArr[12]);
                    double leaseFee = Double.parseDouble(lineArr[13]);

                    contract = new LeaseContract(contractDate, name, email, vehicle, endingValue, leaseFee);
                }

                contractList.add(contract);
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            return contractList;
        }
    }
}
