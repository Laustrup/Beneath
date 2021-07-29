package laustrup.beneath.repositories;

import laustrup.beneath.services.Print;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class Repository {

    private DatabaseConnection database = new DatabaseConnection();
    private Connection connection;

    // Executes PreparedStatement and returns the ResultSet, needs a close connection.
    protected ResultSet getResultSet(String sql) {

        connectionStatus();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            return statement.executeQuery();
        }
        catch (Exception e) {
            System.out.println("Couldn't execute query...\n" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Executes an sql-statement that will update an table in db
    protected void updateTable(String sql, boolean closeConnection) {

        connectionStatus();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Couldn't execute update...\n" + e.getMessage());
            e.printStackTrace();
        }
        if (closeConnection) {
            database.closeConnection();
        }
    }

    private void connectionStatus() {
        if (!database.isConnectionCurrentlyOpen()) {
            if (database.openConnection()) {
                connection = database.getConnection();
            }
        }
    }

    public int findId(String table, String where, String value, String ColumnOfId) {

        connectionStatus();
        ResultSet res = getResultSet("SELECT * FROM " + table + " WHERE " + where + " = '" + value + "';");

        try {
            res.next();
            closeConnection();
            return res.getInt(ColumnOfId);
        }
        catch (Exception e) {
            System.out.println("Couldn't find id...\n" + e.getMessage());
            e.printStackTrace();
            closeConnection();
            return -1;
        }
    }

    protected int calcNextId(String table) {

        connectionStatus();
        ResultSet res = getResultSet("SELECT * FROM " + table + ";");

        int nextId = 0;

        try {
            while (res.next()) {
                nextId++;
            }
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Something went wrong with calculating next id...\n" + e.getMessage());
            closeConnection();
            return -1;
        }
        return nextId + 1;
    }

    public void closeConnection() {
        if (database.isConnectionCurrentlyOpen()) {
            try {
                connection.close();
            }
            catch (Exception e) {
                new Print().writeErr("Couldn't close connection...");
            }
        }
    }

}
