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


-- INSERTING FORD Dealership
INSERT INTO `Dealerships` (`Name`, `Address`, `Phone`)
VALUES ('Ford Dealership', '4567 Ford Rd, Dearborn, MI', '313-555-9876');

INSERT INTO `Vehicles` (`VIN`, `Year`, `Make`, `Model`, `Type`, `Color`, `Mileage`, `Price`)
VALUES
('66789', 2019, 'Ford', 'F-150', 'Truck', 'Black', 62000, 34995.00),
('77890', 2020, 'Ford', 'Mustang', 'Coupe', 'Red', 12000, 39995.00),
('88901', 2022, 'Ford', 'Explorer', 'SUV', 'Gray', 8000, 45995.00),
('99012', 2018, 'Ford', 'Escape', 'SUV', 'Blue', 45000, 21995.00),
('10123', 2021, 'Ford', 'Bronco', 'SUV', 'Green', 10000, 52995.00);

INSERT INTO `Inventory` (`DealershipID`, `VIN`)
VALUES
(1, '66789'),
(1, '77890'),
(1, '88901'),
(1, '99012'),
(1, '10123');


-- INSERTING Toyota Dealership
INSERT INTO `Dealerships` (`Name`, `Address`, `Phone`)
VALUES ('Toyota Dealership', '1234 Toyota Blvd, Torrance, CA', '310-555-1234');

INSERT INTO `Vehicles` (`VIN`, `Year`, `Make`, `Model`, `Type`, `Color`, `Mileage`, `Price`)
VALUES
('11234', 2018, 'Toyota', 'Camry', 'Sedan', 'White', 42000, 18995.00),
('22345', 2021, 'Toyota', 'RAV4', 'SUV', 'Blue', 15000, 29995.00),
('33456', 2017, 'Toyota', 'Tacoma', 'Truck', 'Black', 83000, 25995.00),
('44567', 2023, 'Toyota', 'Highlander', 'SUV', 'Silver', 5000, 47995.00),
('55678', 2020, 'Toyota', 'Corolla', 'Sedan', 'Red', 25000, 18995.00);

INSERT INTO `Inventory` (`DealershipID`, `VIN`)
VALUES
(2, '11234'),
(2, '22345'),
(2, '33456'),
(2, '44567'),
(2, '55678');

-- INSERTING Tesla Dealership
INSERT INTO `Dealerships` (`Name`, `Address`, `Phone`)
VALUES ('Tesla Dealership', '7890 Elon Way, Palo Alto, CA', '650-555-4321');

INSERT INTO `Vehicles` (`VIN`, `Year`, `Make`, `Model`, `Type`, `Color`, `Mileage`, `Price`)
VALUES
('11111', 2022, 'Tesla', 'Model S', 'Sedan', 'Black', 12000, 89999.00),
('22222', 2021, 'Tesla', 'Model 3', 'Sedan', 'White', 30000, 45999.00),
('33333', 2023, 'Tesla', 'Cybertruck', 'Truck', 'Silver', 50, 79999.00),
('44444', 2020, 'Tesla', 'Model X', 'SUV', 'Blue', 25000, 99999.00),
('55555', 2023, 'Tesla', 'Model Y', 'SUV', 'Red', 5000, 64999.00);

INSERT INTO `Inventory` (`DealershipID`, `VIN`)
VALUES
(3, '11111'),
(3, '22222'),
(3, '33333'),
(3, '44444'),
(3, '55555');