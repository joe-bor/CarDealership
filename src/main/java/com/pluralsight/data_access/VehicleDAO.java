package com.pluralsight.data_access;

import com.pluralsight.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO extends AbstractDAO {

    // CREATE
    public void addVehicle(int vin, int year, String make, String model, String vehicleType, String color, int mileage, double price) {
        String query = """
                INSERT INTO Vehicles
                (`VIN`, `Year`, `Make`, `Model`, `Type`, `Color`, `Mileage`, `Price`)
                VALUES
                (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, vin);
            preparedStatement.setInt(2, year);
            preparedStatement.setString(3, make);
            preparedStatement.setString(4, model);
            preparedStatement.setString(5, vehicleType);
            preparedStatement.setString(6, color);
            preparedStatement.setInt(7, mileage);
            preparedStatement.setDouble(8, price);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.printf("Vehicle with VIN #%d was successfully added.\n", vin);
            } else {
                System.out.println("Vehicle insertion failed.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error adding vehicle with VIN " + vin, e);
        }
    }

    // READ
    public List<Vehicle> getVehicleByPriceRange(double min, double max) {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
                SELECT *
                FROM Vehicles
                WHERE PRICE BETWEEN ? AND ?
                """;

        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setDouble(1, min);
            preparedStatement.setDouble(2, max);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(
                            new Vehicle(resultSet.getInt("VIN"), resultSet.getInt("Year"), resultSet.getString("Make"), resultSet.getString("Model"), resultSet.getString("Type"), resultSet.getString("Color"), resultSet.getInt("Mileage"), resultSet.getDouble("Price")
                            ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vehicles;
    }

    public List<Vehicle> getVehicleByMakeModel(String make, String model) {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
                SELECT *
                FROM Vehicles
                WHERE make LIKE ?
                AND model like ?
                """;

        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, "%" + make + "%");
            preparedStatement.setString(2, "%" + model + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(
                            new Vehicle(resultSet.getInt("VIN"), resultSet.getInt("Year"), resultSet.getString("Make"), resultSet.getString("Model"), resultSet.getString("Type"), resultSet.getString("Color"), resultSet.getInt("Mileage"), resultSet.getDouble("Price")
                            ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vehicles;
    }

    public List<Vehicle> getVehicleByYearRange(int min, int max) {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
                SELECT *
                FROM Vehicles
                WHERE year BETWEEN ? AND ?
                """;

        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, min);
            preparedStatement.setInt(2, max);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(
                            new Vehicle(resultSet.getInt("VIN"), resultSet.getInt("Year"), resultSet.getString("Make"), resultSet.getString("Model"), resultSet.getString("Type"), resultSet.getString("Color"), resultSet.getInt("Mileage"), resultSet.getDouble("Price")
                            ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vehicles;
    }

    public List<Vehicle> getVehicleByColor(String color) {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
                SELECT *
                FROM Vehicles
                WHERE color = ?
                """;

        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, color);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(
                            new Vehicle(resultSet.getInt("VIN"), resultSet.getInt("Year"), resultSet.getString("Make"), resultSet.getString("Model"), resultSet.getString("Type"), resultSet.getString("Color"), resultSet.getInt("Mileage"), resultSet.getDouble("Price")
                            ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vehicles;
    }

    public List<Vehicle> getVehicleByMileageRange(int min, int max) {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
                SELECT *
                FROM Vehicles
                WHERE mileage BETWEEN ? AND ?
                """;

        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, min);
            preparedStatement.setInt(2, max);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(
                            new Vehicle(resultSet.getInt("VIN"), resultSet.getInt("Year"), resultSet.getString("Make"), resultSet.getString("Model"), resultSet.getString("Type"), resultSet.getString("Color"), resultSet.getInt("Mileage"), resultSet.getDouble("Price")
                            ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vehicles;
    }

    public List<Vehicle> getVehicleByType(String type) {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
                SELECT *
                FROM Vehicles
                WHERE type = ?
                """;

        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, type);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(
                            new Vehicle(resultSet.getInt("VIN"), resultSet.getInt("Year"), resultSet.getString("Make"), resultSet.getString("Model"), resultSet.getString("Type"), resultSet.getString("Color"), resultSet.getInt("Mileage"), resultSet.getDouble("Price")
                            ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return vehicles;
    }

    public List<Vehicle> getVehiclesOfADealership(int dealershipID) {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
                SELECT *
                FROM Vehicles v
                JOIN Inventory i
                ON v.VIN = i.VIN
                JOIN Dealerships d
                ON i.DealershipID = d.DealershipID
                WHERE d.DealershipID = ?
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, dealershipID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(
                            new Vehicle(resultSet.getInt("VIN"), resultSet.getInt("Year"), resultSet.getString("Make"), resultSet.getString("Model"), resultSet.getString("Type"), resultSet.getString("Color"), resultSet.getInt("Mileage"), resultSet.getDouble("Price")
                            ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    public Vehicle getVehicleOfASalesContract(int contractID) {

        String query = """
                SELECT *
                FROM Vehicles v
                JOIN `SalesContracts` sc
                ON v.VIN = sc.VIN
                WHERE sc.ContractID = ?
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, contractID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return
                            new Vehicle(resultSet.getInt("VIN"), resultSet.getInt("Year"), resultSet.getString("Make"), resultSet.getString("Model"), resultSet.getString("Type"), resultSet.getString("Color"), resultSet.getInt("Mileage"), resultSet.getDouble("Price"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Vehicle getVehicleOfALeaseContract(int contractID) {

        String query = """
                SELECT *
                FROM Vehicles v
                JOIN `LeaseContracts` lc
                ON v.VIN = lc.VIN
                WHERE lc.ContractID = ?
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, contractID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return
                            new Vehicle(resultSet.getInt("VIN"), resultSet.getInt("Year"), resultSet.getString("Make"), resultSet.getString("Model"), resultSet.getString("Type"), resultSet.getString("Color"), resultSet.getInt("Mileage"), resultSet.getDouble("Price"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // UPDATE

    // DELETE
    public void removeVehicle(int vin) {
        String query = """
                DELETE FROM Vehicles
                WHERE vin = ?
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, vin);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.printf("Vehicle with VIN #%d was successfully removed. \n", vin);
            } else {
                System.out.printf("No vehicle found with VIN #%d to remove. \n", vin);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
