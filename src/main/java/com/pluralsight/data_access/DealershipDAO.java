package com.pluralsight.data_access;

import com.pluralsight.model.Dealership;
import com.pluralsight.model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DealershipDAO extends AbstractDAO {

    // READ
    public Dealership getDealershipByID(int id) {
        String query = """
                SELECT *
                FROM Dealerships
                WHERE DealershipID = ?
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    var vehicles = new VehicleDAO().getVehiclesOfADealership(resultSet.getInt("DealershipID"));

                    return new Dealership(resultSet.getInt("DealershipID"), resultSet.getString("name"), resultSet.getString("address"), resultSet.getString("Phone"), vehicles);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Dealership> getAllDealerships() {
        List<Dealership> dealerships = new ArrayList<>();

        String query = """
                SELECT *
                FROM Dealerships
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                dealerships.add(
                        new Dealership(resultSet.getInt("DealershipID"), resultSet.getString("name"), resultSet.getString("address"), resultSet.getString("Phone"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dealerships;
    }


}
