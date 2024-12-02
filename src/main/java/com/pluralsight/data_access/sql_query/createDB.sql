DROP DATABASE IF EXISTS CarDealershipDatabase;

CREATE DATABASE IF NOT EXISTS CarDealershipDatabase;

USE CarDealershipDatabase;

-- Dealership Table
CREATE TABLE `Dealerships` (
    `DealershipID` INTEGER NOT NULL AUTO_INCREMENT,
    `Name` VARCHAR(100) NOT NULL,
    `Address` VARCHAR(255) NOT NULL,
    `Phone` VARCHAR(15) NOT NULL,
    CONSTRAINT `PK_Dealerships` PRIMARY KEY (`DealershipID`)
);

-- Vehicles Table
CREATE TABLE `Vehicles` (
    `VIN` VARCHAR(25) NOT NULL,
    `Year` INTEGER,
    `Make` VARCHAR(50) NOT NULL,
    `Model` VARCHAR(50) NOT NULL,
    `Type` VARCHAR(25),
    `Color` VARCHAR(30),
    `Mileage` INTEGER DEFAULT 0 CHECK (`Mileage` >= 0),
    `Price` DECIMAL(10, 2) DEFAULT 0.00 CHECK (`Price` >= 0),
    CONSTRAINT `PK_Vehicles` PRIMARY KEY (`VIN`)
);

-- Inventory Table
CREATE TABLE `Inventory` (
    `DealershipID` INTEGER NOT NULL,
    `VIN` VARCHAR(25) NOT NULL,
    CONSTRAINT `FK_Inventory_Dealership` FOREIGN KEY (`DealershipID`) REFERENCES `Dealerships` (`DealershipID`),
    CONSTRAINT `FK_Inventory_Vehicles` FOREIGN KEY (`VIN`) REFERENCES `Vehicles` (`VIN`),
    PRIMARY KEY (`DealershipID`, `VIN`) -- Composite primary key
);

-- LeaseContracts Table
CREATE TABLE `LeaseContracts` (
    `ContractID` INTEGER NOT NULL AUTO_INCREMENT,
    `Date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `CustomerName` VARCHAR(100) NOT NULL,
    `CustomerEmail` VARCHAR(100) NOT NULL,
    `VIN` VARCHAR(25) NOT NULL,
    `TotalPrice` DECIMAL(10, 2) DEFAULT 0.00 CHECK (`TotalPrice` >= 0),
    `MonthlyPayment` DECIMAL(10, 2) DEFAULT 0.00 CHECK (`MonthlyPayment` >= 0),
    `ExpectedEndingValue` DECIMAL(10, 2) NOT NULL CHECK (`ExpectedEndingValue` >= 0),
    `LeaseFee` DECIMAL(10, 2) NOT NULL CHECK (`LeaseFee` >= 0),
    CONSTRAINT `FK_LeaseContracts_Vehicles` FOREIGN KEY (`VIN`) REFERENCES `Vehicles` (`VIN`),
    CONSTRAINT `PK_LeaseContracts` PRIMARY KEY (`ContractID`)
);

-- SalesContracts Table
CREATE TABLE `SalesContracts` (
    `ContractID` INTEGER NOT NULL AUTO_INCREMENT,
    `Date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `CustomerName` VARCHAR(100) NOT NULL,
    `CustomerEmail` VARCHAR(100) NOT NULL,
    `VIN` VARCHAR(25) NOT NULL,
    `TotalPrice` DECIMAL(10, 2) DEFAULT 0.00 CHECK (`TotalPrice` >= 0),
    `MonthlyPayment` DECIMAL(10, 2) DEFAULT 0.00 CHECK (`MonthlyPayment` >= 0),
    `SalesTax` DECIMAL(5, 2) NOT NULL CHECK (`SalesTax` >= 0 AND `SalesTax` <= 100),
    `RecordingFee` DECIMAL(10, 2) NOT NULL CHECK (`RecordingFee` >= 0),
    `ProcessingFee` DECIMAL(10, 2) NOT NULL CHECK (`ProcessingFee` >= 0),
    `Financed` BOOLEAN NOT NULL,
    CONSTRAINT `FK_SalesContracts_Vehicles` FOREIGN KEY (`VIN`) REFERENCES `Vehicles` (`VIN`),
    CONSTRAINT `PK_SalesContracts` PRIMARY KEY (`ContractID`)
);
