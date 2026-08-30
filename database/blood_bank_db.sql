-- ============================================================
-- BLOOD BANK MANAGEMENT SYSTEM - DATABASE SCRIPT
-- Database: blood_bank_db
-- Run this script in MySQL to create the database and sample data.
-- ============================================================

-- Create the database
CREATE DATABASE IF NOT EXISTS blood_bank_db;
USE blood_bank_db;

-- ------------------------------------------------------------
-- Table: users (registered application users)
-- Password stored as a SHA-256 hash (never plain text).
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    full_name   VARCHAR(100) NOT NULL,
    email       VARCHAR(100) NOT NULL UNIQUE,
    phone       VARCHAR(15)  NOT NULL,
    password    VARCHAR(64)  NOT NULL,
    blood_group VARCHAR(3)   NOT NULL,
    address     VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ------------------------------------------------------------
-- Table: donors (blood donors)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS donors (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    donor_name          VARCHAR(100) NOT NULL,
    age                 INT NOT NULL CHECK (age >= 18),
    gender              VARCHAR(10)  NOT NULL,
    blood_group         VARCHAR(3)   NOT NULL,
    phone               VARCHAR(15)  NOT NULL,
    email               VARCHAR(100),
    address             VARCHAR(255) NOT NULL,
    last_donation_date  DATE,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ------------------------------------------------------------
-- Table: blood_stock (available blood units)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS blood_stock (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    blood_group  VARCHAR(3) NOT NULL UNIQUE,
    units        INT NOT NULL DEFAULT 0 CHECK (units >= 0),
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ------------------------------------------------------------
-- Table: blood_requests (requests made by users)
-- Status values: Pending, Approved, Rejected
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS blood_requests (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    patient_name    VARCHAR(100) NOT NULL,
    blood_group     VARCHAR(3)   NOT NULL,
    required_units  INT NOT NULL CHECK (required_units > 0),
    hospital_name   VARCHAR(150) NOT NULL,
    contact_number  VARCHAR(15)  NOT NULL,
    request_date    DATE,
    address         VARCHAR(255),
    reason          VARCHAR(255),
    status          VARCHAR(20)  NOT NULL DEFAULT 'Pending',
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ------------------------------------------------------------
-- Table: admin (administrator login)
-- Password is the SHA-256 hash of "admin123".
-- Hash: 240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS admin (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL
);

-- ============================================================
-- SAMPLE DATA
-- ============================================================

-- Sample admin login (username: admin / password: admin123)
INSERT INTO admin (username, password)
VALUES ('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9');

-- Sample blood stock
INSERT INTO blood_stock (blood_group, units) VALUES
    ('A+', 25),
    ('A-', 12),
    ('B+', 20),
    ('B-', 10),
    ('AB+', 8),
    ('AB-', 5),
    ('O+', 30),
    ('O-', 15);

-- Sample donors
INSERT INTO donors (donor_name, age, gender, blood_group, phone, email, address, last_donation_date) VALUES
    ('Rahul Sharma', 28, 'Male',   'O+',  '9876543210', 'rahul.sharma@example.com',  'Mumbai, Maharashtra',  '2026-01-15'),
    ('Priya Patel',  25, 'Female', 'A+',  '9876501234', 'priya.patel@example.com',   'Pune, Maharashtra',    '2026-02-10'),
    ('Amit Kumar',   32, 'Male',   'B+',  '9812345678', 'amit.kumar@example.com',    'Delhi',                '2026-01-05'),
    ('Sneha Reddy',  27, 'Female', 'AB+', '9850123456', 'sneha.reddy@example.com',   'Hyderabad, Telangana', '2025-12-20'),
    ('Vikram Singh', 35, 'Male',   'O-',  '9867543210', 'vikram.singh@example.com',  'Jaipur, Rajasthan',    '2025-11-30');

-- Sample registered users (password stored as SHA-256 hash of "password123")
INSERT INTO users (full_name, email, phone, password, blood_group, address) VALUES
    ('John Doe',    'john.doe@example.com',    '9123456780', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'A+', 'Bengaluru, Karnataka'),
    ('Jane Smith',  'jane.smith@example.com',  '9234567890', 'ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f', 'O-', 'Chennai, Tamil Nadu');

-- Sample blood requests (default status Pending)
INSERT INTO blood_requests (patient_name, blood_group, required_units, hospital_name, contact_number, request_date, address, reason)
VALUES
    ('Anil Verma',    'O+', 2, 'City General Hospital', '9876001111', '2026-02-01', 'Mumbai',    'Emergency surgery'),
    ('Meera Nair',    'B+', 1, 'Apollo Hospital',       '9876002222', '2026-02-05', 'Chennai',   'Accident treatment');

-- ============================================================
-- NOTE: To regenerate the password hash for any user, the SHA-256
-- of the plain text password is used. The sample users above all
-- use password "password123" (hash ef92b778...). New registrations
-- are hashed automatically by the Java PasswordUtil class.
-- ============================================================
