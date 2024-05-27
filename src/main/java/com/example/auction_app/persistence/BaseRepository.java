package com.example.auction_app.persistence;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseRepository {
    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }
}
