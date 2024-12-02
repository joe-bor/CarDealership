package com.pluralsight.data_access;

import com.pluralsight.model.contract.Contract;
import com.pluralsight.model.contract.LeaseContract;

import java.sql.*;

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
}
