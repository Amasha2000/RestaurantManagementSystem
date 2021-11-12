#mysql -h localhost -p 1234 -u root

DROP DATABASE IF EXISTS RestaurantManagementSystem;
CREATE DATABASE IF NOT EXISTS RestaurantManagementSystem;
SHOW DATABASES;
USE RestaurantManagementSystem;
#--------------------------------------
DROP TABLE IF EXISTS Owner;
CREATE TABLE IF NOT EXISTS Owner(
    ownerId VARCHAR(15) NOT NULL,
    name VARCHAR(45) NOT NULL DEFAULT 'Unknown',
    password VARCHAR(8),
    CONSTRAINT PRIMARY KEY(ownerId)
);
SHOW TABLES;
DESC Owner;
#----------------------------------------
DROP TABLE IF EXISTS User;
CREATE TABLE IF NOT EXISTS User(
    userId VARCHAR(15) NOT NULL,
    userName VARCHAR(45) NOT NULL DEFAULT 'Unknown',
    password VARCHAR(8) NOT NULL UNIQUE,
    owId VARCHAR(15),
    CONSTRAINT PRIMARY KEY(userId),
    CONSTRAINT FOREIGN KEY(owId) REFERENCES Owner(ownerId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESC User;
#-------------------------------------------
DROP TABLE IF  EXISTS Employee;
CREATE TABLE IF NOT EXISTS Employee(
    empId VARCHAR(15) NOT NULL,
    empName VARCHAR(45) NOT NULL DEFAULT 'Unknown',
    empAddress VARCHAR(60),
    empNic VARCHAR(20) UNIQUE,
    empPhoneNumber VARCHAR(15),
    empDescription VARCHAR(10),
    ownId VARCHAR(15),
        CONSTRAINT PRIMARY KEY(empId),
        CONSTRAINT FOREIGN KEY(ownId) REFERENCES Owner(ownerId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESC Employee;
#--------------------------------------------------
DROP TABLE IF EXISTS Tabless;
CREATE TABLE IF NOT EXISTS Tabless(
    tId INT NOT NULL AUTO_INCREMENT,
    table_number INT UNIQUE,
    table_code VARCHAR(10) UNIQUE,
    numberOfSeats INT DEFAULT 0,
    adId VARCHAR(15),
    CONSTRAINT PRIMARY KEY(tId),
    CONSTRAINT FOREIGN KEY(adId) REFERENCES Owner(ownerId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESC Tabless;
#----------------------------------------------------
DROP TABLE IF EXISTS Meal;
CREATE TABLE IF NOT EXISTS Meal(
    categories VARCHAR(20),
    subCategories VARCHAR(60) NOT NULL,
    unitPrice DECIMAL(6,2),
    adminId VARCHAR(15),
    CONSTRAINT PRIMARY KEY(subCategories),
    CONSTRAINT FOREIGN KEY(adminId) REFERENCES Owner(ownerId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESC Meal;
#-----------------------------------------------------
DROP TABLE IF EXISTS Orders;
CREATE TABLE IF NOT EXISTS Orders(
    oId VARCHAR(15) NOT NULL,
    tblCode VARCHAR(10) ,
    date VARCHAR(20),
    time VARCHAR(20),
    cost DECIMAL(6,2),
    employeeId VARCHAR(15),
    cashId VARCHAR(15),
    tableId INT,
    CONSTRAINT PRIMARY KEY(oId),
    CONSTRAINT FOREIGN KEY(employeeId) REFERENCES Employee(empId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY(cashId) REFERENCES User(userId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY(tableId) REFERENCES Tabless(tId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESC Orders;
#-------------------------------------------------------
DROP TABLE IF EXISTS OrderCopy;
CREATE TABLE IF NOT EXISTS OrderCopy(
    orderId VARCHAR(15) NOT NULL,
    tblCode VARCHAR(10) UNIQUE,
    date VARCHAR(20),
    time VARCHAR(20),
    cost DECIMAL(6,2),
    employeeId VARCHAR(15),
    cashId VARCHAR(15),
    tableId INT,
    CONSTRAINT PRIMARY KEY(orderId),
    CONSTRAINT FOREIGN KEY(employeeId) REFERENCES Employee(empId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY(cashId) REFERENCES User(userId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY(tableId) REFERENCES Tabless(tId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESC OrderCopy;
#--------------------------------------------------------
DROP TABLE IF EXISTS OrderDetail;
CREATE TABLE IF NOT EXISTS OrderDetail(
    code VARCHAR(15) NOT NULL,
    mealType VARCHAR(20),
    quantity INT DEFAULT 0,
    price DECIMAL(6,2),
    CONSTRAINT PRIMARY KEY(code,mealType),
    CONSTRAINT FOREIGN KEY(code) REFERENCES Orders(oId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY(mealType) REFERENCES Meal(subCategories) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESC OrderDetail;
#-----------------------------------------------------------

