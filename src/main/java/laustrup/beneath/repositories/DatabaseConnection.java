package laustrup.beneath.repositories;

import laustrup.beneath.services.Printer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {

    private Properties properties;
    private Connection connection;

    private String dbConnection;
    private String username;
    private String password;

    private boolean isConnectionOpen = false;

    // Method to create connection, if it fails it returns null, otherwise returns the created connection
    public boolean openConnection() {
        try (InputStream stream = new FileInputStream("src/main/resources/application.properties")){
            properties = new Properties();
            properties.load(stream);
            dbConnection = properties.getProperty("connection");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            connection = DriverManager.getConnection(dbConnection,username,password);
            new Printer().writeMessage("Connection " + connection + " is opened!");
            isConnectionOpen = true;
            return true;
        }
        catch (Exception e) {
            new Printer().writeExceptionErr("Couldn't create connection...",e);
            return false;
        }
    }

    public boolean closeConnection() {
        try {
            connection.close();
            isConnectionOpen = false;
            return true;
        }
        catch (java.lang.Exception e) {
            new Printer().writeErr("Couldn't close connection...");
            return false;
        }
    }

    public boolean isConnectionCurrentlyOpen() {
        return isConnectionOpen;
    }

    public Connection getConnection() {
        return connection;
    }
}
