package com.pluralsight.data_access;

import com.pluralsight.model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VehicleDAO extends AbstractDAO {

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

    public List<Vehicle> getVehicleByPMakeModel(String make, String model) {
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
            preparedStatement.setString(1, "'%" + make + "'");
            preparedStatement.setString(2, "'%" + model + "'");

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

}
