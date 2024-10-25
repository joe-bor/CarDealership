package com.pluralsight;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Dealership dealership;
    private static final Scanner SCANNER = new Scanner(System.in);

    public void display() {
        init();

        boolean isRunning = true;
        do {
            System.out.println("""
                    ========================================================
                                    MAIN MENU
                    ========================================================
                    What would you to do?
                    1 - Find vehicles within a price range
                    2 - Find vehicles by make/model
                    3 - Find vehicles by year range
                    4 - Find vehicles by color
                    5 - Find vehicles by mileage range
                    6 - Find vehicles by type (car, truck, SUV, van)
                    7 - List ALL Vehicles
                    8 - Add a vehicle
                    9 - Remove a vehicle
                    99 - Quit
                    
                    """);
            String answer = SCANNER.nextLine().trim();

            switch (answer) {
                case "1" -> processGetByPriceRequest();
                case "2" -> processGetByMakeModelRequest();
                case "3" -> processGetByYearRequest();
                case "4" -> processGetByColorRequest();
                case "5" -> processGetByMileageRequest();
                case "6" -> processGetByVehicleTypeRequest();
                case "7" -> processAllVehiclesRequest();
                case "8" -> processAddVehicleRequest();
                case "9" -> processRemoveVehicleRequest();
                case "99" -> {
                    isRunning = false;
                    System.out.println("Terminating...");
                }
                default -> System.err.println("Invalid Option. Try again!");
            }

        } while (isRunning);
        SCANNER.close();
    }

    public void processGetByPriceRequest() {
        System.out.print("What is the min price? ");
        double minPrice = SCANNER.nextDouble();
        SCANNER.nextLine();
        System.out.print("What is the max price? ");
        double maxPrice = SCANNER.nextDouble();
        SCANNER.nextLine();

        displayVehicles(this.dealership.getVehiclesByPrice(minPrice, maxPrice));
    }

    public void processGetByMakeModelRequest() {
        System.out.print("What is the make? ");
        String make = SCANNER.nextLine();
        System.out.print("What is the model? ");
        String model = SCANNER.nextLine();

        displayVehicles(this.dealership.getVehiclesByMakeModel(make, model));
    }

    public void processGetByYearRequest() {
        System.out.print("What is the min year? ");
        int minYear = SCANNER.nextInt();
        SCANNER.nextLine();
        System.out.print("What is the max year? ");
        int maxYear = SCANNER.nextInt();
        SCANNER.nextLine();

        displayVehicles(this.dealership.getVehicleByYear(minYear, maxYear));
    }

    public void processGetByColorRequest() {
        System.out.print("What is the color? ");
        String color = SCANNER.nextLine();

        displayVehicles(this.dealership.getVehicleByColor(color));
    }

    public void processGetByMileageRequest() {
        System.out.print("What is the min mileage? ");
        int minMileage = SCANNER.nextInt();
        SCANNER.nextLine();
        System.out.print("What is the max mileage? ");
        int maxMileage = SCANNER.nextInt();
        SCANNER.nextLine();

        displayVehicles(this.dealership.getVehicleByMileage(minMileage, maxMileage));
    }

    public void processGetByVehicleTypeRequest() {
        System.out.print("What is the vehicle type? ");
        String vehicleType = SCANNER.nextLine();

        displayVehicles(this.dealership.getVehicleByType(vehicleType));
    }

    public void processGetAllVehiclesRequest() {
        displayVehicles(this.dealership.getAllVehicles());
    }

    public void processAddVehicleRequest() {
        System.out.print("What is the vehicle vin? ");
        int vin = SCANNER.nextInt();
        SCANNER.nextLine();
        System.out.print("What is the vehicle year? ");
        int year = SCANNER.nextInt();
        SCANNER.nextLine();
        System.out.print("What is the vehicle make? ");
        String make = SCANNER.nextLine();
        System.out.print("What is the vehicle model? ");
        String model = SCANNER.nextLine();
        System.out.print("What is the vehicle type? ");
        String vehicleType = SCANNER.nextLine();
        System.out.print("What is the vehicle color? ");
        String color = SCANNER.nextLine();
        System.out.print("What is the vehicle mileage? ");
        int mileage = SCANNER.nextInt();
        SCANNER.nextLine();
        System.out.print("What is the vehicle price? ");
        double price = SCANNER.nextDouble();
        SCANNER.nextLine();

        System.out.println("Adding new vehicle to inventory... ");
        this.dealership.addVehicle(new Vehicle(vin, year, make, model, vehicleType, color, mileage, price));
        DealershipFileManager dfm = new DealershipFileManager();
        dfm.saveDealership(this.dealership);
        System.out.println("Successfully added new vehicle!\n");
    }

    public void processRemoveVehicleRequest() {
        // TODO: implement!!
//        this.dealership.removeVehicle();
    }

    private void init() {
        DealershipFileManager dfm = new DealershipFileManager();
        this.dealership = dfm.getDealership();
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        for (Vehicle v : vehicles) {
            System.out.println(v);
        }
    }

    public void processAllVehiclesRequest() {
        displayVehicles(this.dealership.getAllVehicles());
    }
}
