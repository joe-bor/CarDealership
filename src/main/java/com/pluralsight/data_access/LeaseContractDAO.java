package com.pluralsight.data_access;

import com.pluralsight.model.contract.Contract;
import com.pluralsight.model.contract.LeaseContract;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaseContractDAO extends AbstractDAO {

    // CREATE
    public <T extends Contract> void createLeaseContract(LeaseContract leaseContract) {
        String query = """
                INSERT INTO `LeaseContracts`
                (`CustomerName`, `CustomerEmail`, `VIN`, `TotalPrice`, `MonthlyPayment`, `ExpectedEndingValue`, `LeaseFee`)
                VALUES
                (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, leaseContract.getCustomerName());
            preparedStatement.setString(2, leaseContract.getCustomerEmail());
            preparedStatement.setInt(3, leaseContract.getVehicleSold().getVin());
            preparedStatement.setDouble(4, leaseContract.getTotalPrice());
            preparedStatement.setDouble(5, leaseContract.getMonthlyPayment());
            preparedStatement.setDouble(6, leaseContract.getExpectedEndingValue());
            preparedStatement.setDouble(7, leaseContract.getLeaseFee());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Lease contract successfully created.");
            } else {
                System.out.println("Failed to create lease contract.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error creating lease contract", e);
        }
    }

    // READ
    public List<LeaseContract> readLeaseContracts() {
        String query = """
                SELECT * FROM `LeaseContracts`
                """;
        List<LeaseContract> contracts = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                LeaseContract contract = new LeaseContract(
                        resultSet.getString("Date"),
                        resultSet.getString("CustomerName"),
                        resultSet.getString("CustomerEmail"),
                        new VehicleDAO().getVehicleOfALeaseContract(resultSet.getInt("ContractID")),
                        resultSet.getDouble("ExpectedEndingValue"),
                        resultSet.getDouble("LeaseFee")
                );
                contracts.add(contract);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error reading lease contracts", e);
        }

        return contracts;
    }
}
