package com.example.auction_app.persistence;

import com.example.auction_app.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements GenericRepository<User> {

    @Override
    public void add(User user) throws SQLException {
        String sql = "INSERT INTO Users (Email, Password, Name, Address, Phone, RegistrationDate) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getPhone());
            stmt.setTimestamp(6, new Timestamp(user.getRegistrationDate().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public User get(int id) {
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        User user = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("UserID"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("Name"),
                        rs.getString("Address"),
                        rs.getString("Phone"),
                        rs.getTimestamp("RegistrationDate")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE Users SET Email = ?, Password = ?, Name = ?, Address = ?, Phone = ? WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getPhone());
            stmt.setInt(6, user.getUserID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getSize() {
        String sql = "SELECT COUNT(*) FROM Users";
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
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE Email = ?";
        User user = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("UserID"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("Name"),
                        rs.getString("Address"),
                        rs.getString("Phone"),
                        rs.getTimestamp("RegistrationDate")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    public List<User> getAll() {
        String sql = "SELECT * FROM Users";
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User(
                        rs.getInt("UserID"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("Name"),
                        rs.getString("Address"),
                        rs.getString("Phone"),
                        rs.getTimestamp("RegistrationDate")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
