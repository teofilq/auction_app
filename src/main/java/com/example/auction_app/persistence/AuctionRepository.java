package com.example.auction_app.persistence;

import com.example.auction_app.models.Auction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuctionRepository implements GenericRepository<Auction> {

    @Override
    public void add(Auction auction) throws SQLException {
        String sql = "INSERT INTO Auctions (ProductID, StartPrice, MinStep, StartDate, EndDate, Status, WinnerID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, auction.getProductID());
            stmt.setDouble(2, auction.getStartPrice());
            stmt.setDouble(3, auction.getMinStep());
            stmt.setTimestamp(4, new Timestamp(auction.getStartDate().getTime()));
            stmt.setTimestamp(5, new Timestamp(auction.getEndDate().getTime()));
            stmt.setString(6, auction.getStatus());
            if (auction.getWinnerID() == null) {
                stmt.setNull(7, Types.INTEGER);
            } else {
                stmt.setInt(7, auction.getWinnerID());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }


    @Override
    public Auction get(int id) {
        String sql = "SELECT * FROM Auctions WHERE AuctionID = ?";
        Auction auction = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                auction = new Auction(
                        rs.getInt("AuctionID"),
                        rs.getInt("ProductID"),
                        rs.getDouble("StartPrice"),
                        rs.getDouble("MinStep"),
                        rs.getTimestamp("StartDate"),
                        rs.getTimestamp("EndDate"),
                        rs.getString("Status"),
                        rs.getInt("WinnerID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auction;
    }

    @Override
    public void update(Auction auction) {
        String sql = "UPDATE Auctions SET ProductID = ?, StartPrice = ?, MinStep = ?, StartDate = ?, EndDate = ?, Status = ?, WinnerID = ? WHERE AuctionID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, auction.getProductID());
            stmt.setDouble(2, auction.getStartPrice());
            stmt.setDouble(3, auction.getMinStep());
            stmt.setTimestamp(4, new Timestamp(auction.getStartDate().getTime()));
            stmt.setTimestamp(5, new Timestamp(auction.getEndDate().getTime()));
            stmt.setString(6, auction.getStatus());
            stmt.setInt(7, auction.getWinnerID());
            stmt.setInt(8, auction.getAuctionID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Auction auction) {
        String sql = "DELETE FROM Auctions WHERE AuctionID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, auction.getAuctionID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getSize() {
        String sql = "SELECT COUNT(*) FROM Auctions";
        int size = 0;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                size = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return size;
    }

    public List<Auction> getAll() {
        String sql = "SELECT * FROM Auctions";
        List<Auction> auctions = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Auction auction = new Auction(
                        rs.getInt("AuctionID"),
                        rs.getInt("ProductID"),
                        rs.getDouble("StartPrice"),
                        rs.getDouble("MinStep"),
                        rs.getTimestamp("StartDate"),
                        rs.getTimestamp("EndDate"),
                        rs.getString("Status"),
                        rs.getInt("WinnerID")
                );
                auctions.add(auction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auctions;
    }
}
