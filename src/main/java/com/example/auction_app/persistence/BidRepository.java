package com.example.auction_app.persistence;

import com.example.auction_app.models.Bid;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BidRepository implements GenericRepository<Bid> {

    @Override
    public void add(Bid bid) {
        String sql = "INSERT INTO Bids (AuctionID, UserID, BidAmount, BidDateTime) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bid.getAuctionID());
            stmt.setInt(2, bid.getUserID());
            stmt.setDouble(3, bid.getBidAmount());
            stmt.setTimestamp(4, new Timestamp(bid.getBidDateTime().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Bid get(int id) {
        String sql = "SELECT * FROM Bids WHERE BidID = ?";
        Bid bid = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bid = new Bid(
                        rs.getInt("BidID"),
                        rs.getInt("AuctionID"),
                        rs.getInt("UserID"),
                        rs.getDouble("BidAmount"),
                        rs.getTimestamp("BidDateTime")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bid;
    }

    @Override
    public void update(Bid bid) {
        String sql = "UPDATE Bids SET AuctionID = ?, UserID = ?, BidAmount = ?, BidDateTime = ? WHERE BidID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bid.getAuctionID());
            stmt.setInt(2, bid.getUserID());
            stmt.setDouble(3, bid.getBidAmount());
            stmt.setTimestamp(4, new Timestamp(bid.getBidDateTime().getTime()));
            stmt.setInt(5, bid.getBidID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Bid bid) {
        String sql = "DELETE FROM Bids WHERE BidID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bid.getBidID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getSize() {
        String sql = "SELECT COUNT(*) FROM Bids";
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

    public List<Bid> getAll() {
        String sql = "SELECT * FROM Bids";
        List<Bid> bids = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Bid bid = new Bid(
                        rs.getInt("BidID"),
                        rs.getInt("AuctionID"),
                        rs.getInt("UserID"),
                        rs.getDouble("BidAmount"),
                        rs.getTimestamp("BidDateTime")
                );
                bids.add(bid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bids;
    }
}
