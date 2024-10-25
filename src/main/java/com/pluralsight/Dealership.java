package com.pluralsight;

import java.util.ArrayList;
import java.util.List;

public class Dealership {
    private String name;
    private String address;
    private String phone;
    private List<Vehicle> inventory;

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    // OTHER METHODS:
    public List<Vehicle> getVehiclesByPrice(double min, double max) {
        return this.getAllVehicles().stream().
                filter(vehicle -> vehicle.getPrice() >= min && vehicle.getPrice() <= max).toList();
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        return this.getAllVehicles().stream()
                .filter(vehicle -> vehicle.getMake().contains(make) && vehicle.getModel().contains(model)).toList();
    }

    public List<Vehicle> getVehicleByYear(int min, int max) {
        return this.getAllVehicles().stream()
                .filter(vehicle -> vehicle.getYear() >= min && vehicle.getYear() <= max).toList();
    }

    public List<Vehicle> getVehicleByColor(String color) {
        return this.getAllVehicles().stream()
                .filter(vehicle -> vehicle.getColor().contains(color)).toList();
    }

    public List<Vehicle> getVehicleByMileage(int min, int max) {
        return this.getAllVehicles().stream()
                .filter(vehicle -> vehicle.getOdometer() >= min && vehicle.getOdometer() <= max).toList();
    }

    public List<Vehicle> getVehicleByType(String vehicleType) {
        return this.getAllVehicles().stream()
                .filter(vehicle -> vehicle.getVehicleType().contains(vehicleType)).toList();
    }

    public List<Vehicle> getAllVehicles() {
        return this.inventory;
    }

    public void addVehicle(Vehicle vehicle) {
        this.getInventory().add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        this.getInventory().remove(vehicle);
    }

    // GETTERS & SETTERS:
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Vehicle> getInventory() {
        return inventory;
    }

    public void setInventory(List<Vehicle> inventory) {
        this.inventory = inventory;
    }
}
