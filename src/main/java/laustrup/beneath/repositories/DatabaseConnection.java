package laustrup.beneath.repositories;

import laustrup.beneath.services.Print;

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

    // Method to create connection, if it fails it returns null, otherwise returns the created connection
    public boolean openConnection() {
        try (InputStream stream = new FileInputStream("src/main/resources/application.properties")){
            properties = new Properties();
            properties.load(stream);
            dbConnection = properties.getProperty("connection");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            connection = DriverManager.getConnection(dbConnection,username,password);
            new Print().writeMessage("Connection " + connection + " is opened!");
            return true;
        }
        catch (Exception e) {
            new Print().writeExceptionErr("Couldn't create connection...",e);
            return false;
        }
    }

    public boolean closeConnection() {
        try {
            connection.close();
            return true;
        }
        catch (java.lang.Exception e) {
            new Print().writeErr("Couldn't close connection...");
            return false;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}