DROP TABLE IF EXISTS Bids;
DROP TABLE IF EXISTS Auctions;
DROP TABLE IF EXISTS Products;
DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
                       UserID INT AUTO_INCREMENT PRIMARY KEY,
                       Email VARCHAR(255) UNIQUE NOT NULL,
                       Password VARCHAR(255),
                       Name VARCHAR(255) NOT NULL,
                       Address VARCHAR(255),
                       Phone VARCHAR(15),
                       RegistrationDate DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Products (
                          ProductID INT AUTO_INCREMENT PRIMARY KEY,
                          UserID INT NOT NULL,
                          Name VARCHAR(255) NOT NULL,
                          Description TEXT,
                          Category VARCHAR(255),
                          AddDate DATETIME DEFAULT CURRENT_TIMESTAMP,
                          ProductCondition VARCHAR(100),
                          ImageURL VARCHAR(255),
                          FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE Auctions (
                          AuctionID INT AUTO_INCREMENT PRIMARY KEY,
                          ProductID INT NOT NULL,
                          StartPrice DECIMAL(10,2),
                          MinStep DECIMAL(10,2) DEFAULT 0,
                          StartDate DATETIME DEFAULT CURRENT_TIMESTAMP,
                          EndDate DATETIME,
                          Status ENUM('Active', 'Ended', 'Cancelled') DEFAULT 'Active',
                          WinnerID INT NULL,
                          FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
                          FOREIGN KEY (WinnerID) REFERENCES Users(UserID)
);
CREATE TABLE Bids (
                      BidID INT AUTO_INCREMENT PRIMARY KEY,
                      AuctionID INT NOT NULL,
                      UserID INT NOT NULL,
                      BidAmount DECIMAL(10,2),
                      BidDateTime DATETIME DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (AuctionID) REFERENCES Auctions(AuctionID),
                      FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

