package com.pluralsight;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserInterface {

    private Dealership dealership;
    private static final Scanner SCANNER = new Scanner(System.in);

    public void display() {
        init();

        boolean isRunning = true;
        do {
            System.out.println(String.format("""
                    
                    ========================================================
                                    MAIN MENU -  %s
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
                    10 - Buy or Lease a vehicle
                    88 - Switch Dealership
                    99 - Quit
                    00 - Admin
                    
                    """, this.dealership.getName()));
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
                case "10" -> processVehicleContract();
                case "88" -> init();
                case "99" -> {
                    isRunning = false;
                    System.out.println("Terminating...");
                }
                case "00" -> processAdminRequest();
                default -> System.err.println("Invalid Option. Try again!");
            }

        } while (isRunning);
        SCANNER.close();
    }

    private void processAdminRequest() {
        AdminInterface adminInterface = new AdminInterface();
        adminInterface.display();
    }

    private void processVehicleContract() {
        System.out.print("\nPlease provide the VIN of the vehicle you would like to buy/lease (a vehicle over 3 yrs old can't be leased) \n");
        int vin = SCANNER.nextInt();
        SCANNER.nextLine();

        Optional<Vehicle> matchingVehicle = this.dealership.getInventory().stream().filter(vehicle -> vehicle.getVin() == vin).findFirst();
        matchingVehicle.ifPresentOrElse(vehicle -> {
                    System.out.println("Matching vehicle found");

                    int vehicleAge = LocalDate.now().getYear() - vehicle.getYear();
                    System.out.printf("Would you like to buy %sthis vehicle? \n", vehicleAge > 3 ? "" : "or rent ");

                    String answer = SCANNER.nextLine();
                    System.out.print("What is your name? \n");
                    String name = SCANNER.nextLine();
                    System.out.print("What is your email? \n");
                    String email = SCANNER.nextLine();
                    String todaysDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

                    Contract contract = null;
                    if (answer.equalsIgnoreCase("buy")) {
                        System.out.print("Would you like to finance? (true or false) \n");
                        boolean financed = SCANNER.nextBoolean();
                        SCANNER.nextLine();

                        final double SALES_TAX = 5.0;
                        final double RECORDING_FEE = 100.0;
                        final double PROCESSING_FEE = vehicle.getPrice() < 10_000 ? 295.0 : 495.0;

                        contract = new SalesContract(todaysDate, name, email, vehicle, SALES_TAX, RECORDING_FEE, PROCESSING_FEE, financed);
                    } else {
                        final double EXPECTED_ENDING_VALUE = vehicle.getPrice() * .5;
                        final double LEASE_FEE = 7;

                        contract = new LeaseContract(todaysDate, name, email, vehicle, EXPECTED_ENDING_VALUE, LEASE_FEE);
                    }

                    if (contract != null) {
                        ContractFileManager contractFileManager = new ContractFileManager();
                        contractFileManager.saveContract(contract);

                        processRemoveVehicleRequest2(vehicle);
                    }

                }
                , () -> System.out.printf("Vehicle with vin %d not found\n", vin));
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
        System.out.println("Provide the VIN of the vehicle you want to remove");
        int vin = SCANNER.nextInt();
        SCANNER.nextLine();
        Vehicle vehicleToBeRemoved = null;

        for (int i = 0; i < this.dealership.getInventory().size(); i++) {
            Vehicle currVehicle = this.dealership.getInventory().get(i);

            if (currVehicle.getVin() == vin) {
                System.out.println("Found matching vehicle... ");
                vehicleToBeRemoved = this.dealership.getInventory().remove(i);
            }
        }

        if (vehicleToBeRemoved != null) {
            System.out.println("Successfully removed vehicle from inventory...");
            DealershipFileManager dfm = new DealershipFileManager();
            System.out.println("\nHere's the current inventory:");
            dfm.saveDealership(this.dealership);
        }

        if (vehicleToBeRemoved == null) {
            System.out.println("Vehicle not found");
        }
    }

    public void processRemoveVehicleRequest2(Vehicle vehicle) {
            this.dealership.getInventory().remove(vehicle);

            DealershipFileManager dfm = new DealershipFileManager();
            System.out.println("\nHere's the current inventory:");
            dfm.saveDealership(this.dealership);
    }

    private void init() {
        String dealershipName = pickDealership();

        DealershipFileManager dfm = new DealershipFileManager();
        this.dealership = dealershipName.trim().isBlank() ? dfm.getDealership() : dfm.getDealership(dealershipName);
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        for (Vehicle v : vehicles) {
            System.out.println(v);
        }
    }

    public void processAllVehiclesRequest() {
        displayVehicles(this.dealership.getAllVehicles());
    }

    private String pickDealership() {
        String root = System.getProperty("user.dir");
        File file = new File(root);
        File[] files = file.listFiles();

        System.out.println("\nPlease pick one from the available dealerships: ");
        for (File f : files) {
            if (f.getName().endsWith(".csv"))
                System.out.println(f.getName().replace(".csv", ""));
        }
        System.out.println("\n**Leave BLANK for default dealership**");
        System.out.print("Enter dealership name: ");
        String dealershipName = SCANNER.nextLine().trim();
        return dealershipName.isBlank() ? "" : dealershipName + ".csv";
    }
}
