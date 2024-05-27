package com.example.auction_app.persistence;

import com.example.auction_app.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements GenericRepository<Product> {

    @Override
    public void add(Product product) {
        String sql = "INSERT INTO Products (UserID, Name, Description, Category, AddDate, ProductCondition, ImageURL) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, product.getUserID());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setString(4, product.getCategory());
            stmt.setTimestamp(5, new Timestamp(product.getAddDate().getTime()));
            stmt.setString(6, product.getProductCondition());
            stmt.setString(7, product.getImageURL());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product get(int id) {
        String sql = "SELECT * FROM Products WHERE ProductID = ?";
        Product product = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                product = new Product(
                        rs.getInt("ProductID"),
                        rs.getInt("UserID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("Category"),
                        rs.getTimestamp("AddDate"),
                        rs.getString("ProductCondition"),
                        rs.getString("ImageURL")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE Products SET UserID = ?, Name = ?, Description = ?, Category = ?, AddDate = ?, ProductCondition = ?, ImageURL = ? WHERE ProductID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, product.getUserID());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setString(4, product.getCategory());
            stmt.setTimestamp(5, new Timestamp(product.getAddDate().getTime()));
            stmt.setString(6, product.getProductCondition());
            stmt.setString(7, product.getImageURL());
            stmt.setInt(8, product.getProductID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Product product) {
        String sql = "DELETE FROM Products WHERE ProductID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, product.getProductID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getSize() {
        String sql = "SELECT COUNT(*) FROM Products";
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

    public List<Product> getAll() {
        String sql = "SELECT * FROM Products";
        List<Product> products = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("ProductID"),
                        rs.getInt("UserID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("Category"),
                        rs.getTimestamp("AddDate"),
                        rs.getString("ProductCondition"),
                        rs.getString("ImageURL")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
