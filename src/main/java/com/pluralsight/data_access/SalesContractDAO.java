package com.pluralsight.data_access;


import com.pluralsight.model.contract.Contract;
import com.pluralsight.model.contract.SalesContract;

import java.sql.*;

public class SalesContractDAO extends AbstractDAO {

    // CREATE
    public <T extends Contract> void createSalesContract(SalesContract salesContract) {
        String query = """
                INSERT INTO `SalesContracts`
                (`CustomerName`, `CustomerEmail`, `VIN`, `TotalPrice`, `MonthlyPayment`, `SalesTax`, `RecordingFee`, `ProcessingFee`, `Financed`)
                VALUES
                (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, salesContract.getCustomerName());
            preparedStatement.setString(2, salesContract.getCustomerEmail());
            preparedStatement.setInt(3, salesContract.getVehicleSold().getVin());
            preparedStatement.setDouble(4, salesContract.getTotalPrice());
            preparedStatement.setDouble(5, salesContract.getMonthlyPayment());
            preparedStatement.setDouble(6, salesContract.getSalesTax());
            preparedStatement.setDouble(7, salesContract.getRecordingFee());
            preparedStatement.setDouble(8, salesContract.getProcessingFee());
            preparedStatement.setBoolean(9, salesContract.isFinanced());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Sales contract successfully created.");
            } else {
                System.out.println("Failed to create sales contract.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error creating sales contract", e);
        }
    }

    // READ
//    public List<SalesContract> readSalesContracts() {
//        String query = """
//                SELECT * FROM `SalesContracts`
//                """;
//        List<SalesContract> contracts = new ArrayList<>();
//
//        try (Connection connection = getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(query);
//             ResultSet resultSet = preparedStatement.executeQuery()) {
//
//            while (resultSet.next()) {
//                SalesContract contract = new SalesContract(
//                        resultSet.getString("Date"),
//                        resultSet.getString("CustomerName"),
//                        resultSet.getString("CustomerEmail"),
//                        resultSet.getString("VIN"), // Vehicle not VIN (needs a new function in VehicleDAO that retrieves Vehicle assosicaited with Contract ID)
//                        resultSet.getDouble("SalesTax"),
//                        resultSet.getDouble("RecordingFee"),
//                        resultSet.getDouble("ProcessingFee"),
//                        resultSet.getBoolean("Financed")
//                );
//                contracts.add(contract);
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error reading sales contracts", e);
//        }
//
//        return contracts;
//    }
}
