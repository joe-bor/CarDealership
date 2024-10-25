package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class DealershipFileManager {

    public Dealership getDealership() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("dealership1.csv"))) {

            String input;
            int lineCount = 0;
            Dealership dealership = null;
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

        return null;
    }

    public void saveDealership(Dealership dealership) {
        // TODO: Implement
    }
}
