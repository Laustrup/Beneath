package laustrup.beneath.repositories;

import laustrup.beneath.services.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class Repository {

    private Database database;
    private Connection connection;

    // Executes PreparedStatement and returns the ResultSet, needs a close connection.
    protected ResultSet getResultSet(String sql) {

        connection = database.getConnection();
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
    protected void updateTable(String sql) {
        connection = database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Couldn't execute update...\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public int findId(String table, String where, String value, String ColumnOfId) {

        ResultSet res = getResultSet("SELECT * FROM " + table + " WHERE " + where + " = '" + value + "';");

        try {
            res.next();
            return res.getInt(ColumnOfId);
        }
        catch (Exception e) {
            System.out.println("Couldn't find id...\n" + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    protected int calcNextId(String table) {
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
        }
        return nextId + 1;
    }


}
