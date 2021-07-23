package laustrup.beneath.services;

import laustrup.beneath.repositories.DatabaseConnection;

import java.sql.Connection;

public class Database {

    private DatabaseConnection db;
    private Connection connection;

    public void openConnection() {
        db.openConnection();
    }

    public boolean closeConnection() {
        return db.closeConnection();
    }

    public Connection getConnection() {
        return db.getConnection();
    }
}
