/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Finance_Tracker;

/**
 *
 * @author TM Sachith
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class dbconnect {
    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/financetracker?zeroDateTimeBehavior=convertToNull";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // Method to authenticate user
    public static boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND pwd = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If user exists, return true
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            return false;
        }
    }

    public static void insertTransaction(String date, double amount, String type, String category) {
    String query = "INSERT INTO tra (date, amount, type, category) VALUES (?, ?, ?, ?)";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, date);
        statement.setDouble(2, amount);
        statement.setString(3, type);
        statement.setString(4, category);
        statement.executeUpdate();
        System.out.println("Transaction inserted successfully!");
    } catch (SQLException e) {
        System.err.println("Error inserting transaction: " + e.getMessage());
    }
}


     public static void insertUser(String username, String dob, String password) {
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         Statement statement = connection.createStatement()) {

        // SQL query to insert data into the database
        String sql = "INSERT INTO test (username, dob, password) VALUES ('" 
                     + username + "', '" + dob + "', '" + password + "')";

        // Execute the insert statement
        int rowsInserted = statement.executeUpdate(sql);
        if (rowsInserted == 1) {
            System.out.println("User inserted successfully!");
        }
    } catch (SQLException e) {
        System.err.println("Error inserting user: " + e.getMessage());
    }
}
     public static double getBalance() {
        double balance = 0.0;
        String query = "SELECT SUM(amount) FROM transactions";
        
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            if (resultSet.next()) {
                balance = resultSet.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving balance: " + e.getMessage());
        }
        
        return balance;
    }
     public static List<List<Object>> getTransactions() {
        List<List<Object>> transactions = new ArrayList<>();
        String query = "SELECT date, amount, type, category FROM transactions";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                List<Object> transaction = new ArrayList<>();
                // Retrieve values for each column
                String date = resultSet.getString("date");
                double amount = resultSet.getDouble("amount");
                String type = resultSet.getString("type");
                String category = resultSet.getString("category");
                // Add values to the transaction list
                transaction.add(date);
                transaction.add(amount);
                transaction.add(type);
                transaction.add(category);
                // Add the transaction list to the list of transactions
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
        }
        return transactions;
    }
     
}

