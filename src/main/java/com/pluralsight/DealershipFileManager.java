package com.pluralsight;

import com.pluralsight.model.Dealership;
import com.pluralsight.model.Vehicle;

import java.io.*;
import java.util.regex.Pattern;

public class DealershipFileManager {
//    public static void main(String[] args) {
//        var x = getDealership();
//        saveDealership(x);
//    }

    public Dealership getDealership() {
        Dealership dealership = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("dealership1.csv"))) {

            String input;
            int lineCount = 0;
            while ((input = bufferedReader.readLine()) != null) {

                // Line 1 => pipe-delimited info about the dealership
                String[] dealerParts = input.split(Pattern.quote("|"));
                if (lineCount == 0) {
                    dealership = new Dealership(dealerParts[0], dealerParts[1], dealerParts[2]);
                } else {
                    // Line 2+ => vehicle information
                    Vehicle vehicle = new Vehicle(Integer.parseInt(dealerParts[0]), Integer.parseInt(dealerParts[1]), dealerParts[2], dealerParts[3], dealerParts[4], dealerParts[5], Integer.parseInt(dealerParts[6]), Double.parseDouble(dealerParts[7]));
                    dealership.addVehicle(vehicle);
                }
                lineCount++;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return dealership;
    }

    public Dealership getDealership(String dealershipName) {
        Dealership dealership = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(dealershipName))) {

            String input;
            int lineCount = 0;
            while ((input = bufferedReader.readLine()) != null) {

                // Line 1 => pipe-delimited info about the dealership
                String[] dealerParts = input.split(Pattern.quote("|"));
                if (lineCount == 0) {
                    dealership = new Dealership(dealerParts[0], dealerParts[1], dealerParts[2]);
                } else {
                    // Line 2+ => vehicle information
                    Vehicle vehicle = new Vehicle(Integer.parseInt(dealerParts[0]), Integer.parseInt(dealerParts[1]), dealerParts[2], dealerParts[3], dealerParts[4], dealerParts[5], Integer.parseInt(dealerParts[6]), Double.parseDouble(dealerParts[7]));
                    dealership.addVehicle(vehicle);
                }
                lineCount++;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return dealership;
    }

    public void saveDealership(Dealership dealership) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("dealership1.csv"))) {
            // Read the properties of the dealership object -> write in the first line
            bufferedWriter.append(String.format("%s|%s|%s\n", dealership.getName(), dealership.getAddress(), dealership.getPhone()));
            // Loop: for every vehicle, write props in line
            for (Vehicle vehicle : dealership.getAllVehicles()) {
                System.out.println(vehicle);
                bufferedWriter.append(String.format("%d|%d|%s|%s|%s|%s|%d|%.2f\n", vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice()));
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
