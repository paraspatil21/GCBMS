CREATE DATABASE IF NOT EXISTS gcbms;
USE gcbms;

CREATE TABLE IF NOT EXISTS Customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    full_name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS Cylinders (
    cylinder_id INT PRIMARY KEY AUTO_INCREMENT,
    category VARCHAR(50),
    weight VARCHAR(20),
    price DECIMAL(10,2)
);

CREATE TABLE IF NOT EXISTS Bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    cylinder_id INT,
    payment_mode VARCHAR(20),
    upi_id VARCHAR(100) NULL,
    transaction_id VARCHAR(100) NULL,
    address TEXT,
    contact VARCHAR(15),
    status VARCHAR(50),
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id),
    FOREIGN KEY (cylinder_id) REFERENCES Cylinders(cylinder_id)
);

-- Insert Cylinders
INSERT INTO Cylinders (category, weight, price) VALUES 
('Domestic', '5 kg LPG', 300.00),
('Domestic', '14.2 kg LPG', 850.00),
('Commercial', '19 kg LPG', 1350.00),
('Commercial', '35 kg LPG', 2200.00),
('Commercial', '47.5 kg LPG', 3100.00);

-- Static customers inserted via init.sql
