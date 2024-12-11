/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financetracker;

/**
 *
 * @author DELL
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class databaseconnection {
    // JDBC URL, username, and password of MySQL server
     static final String JDBC_URL = "jdbc:mysql://localhost:3306/financetracker?zeroDateTimeBehavior=convertToNull";
     static final String USERNAME = "root";
     static final String PASSWORD = "";

    // Method to authenticate user
    public static boolean authenticateUser(String accno, String password) {
        String query = "SELECT * FROM users WHERE nic = ? AND pwd = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, accno);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If user exists, return true
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean niccheck(String nic) {
        String query = "SELECT * FROM users WHERE nic = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nic);
            
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If user exists, return true
        } catch (SQLException e) {
            System.err.println("NIC Error: " + e.getMessage());
            return false;
        }
    }
    
     public static List<Object[]> reportset(String query) throws SQLException {
        List<Object[]> data = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            while (resultSet.next()) {
                Object[] row = new Object[numberOfColumns];
                for (int i = 0; i < numberOfColumns; i++) {
                    row[i] = resultSet.getObject(i + 1);
                }
                data.add(row);
            }
        }

        return data;
    }
    
    public static boolean budgetcheck(String id) {
        String query = "SELECT * FROM budget WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If user exists, return true
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    public static Double getBudget(String id) {
    String query = "SELECT amount FROM budget WHERE id = ?";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, id);
        
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble("amount"); // Return the name if user exists
        }
    } catch (SQLException e) {
        System.err.println("Error authenticating user: " + e.getMessage());
    }
    return 0.0; // Return null if user does not exist
}
    
    public static void updateBudget(String id, double am) {
        
    String updateQuery = "UPDATE budget SET amount = ? WHERE id = ?";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = connection.prepareStatement(updateQuery)) {
        // Set parameters for the update query
        statement.setDouble(1, am);
        statement.setString(2, id);
        // Execute the update query
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("updated successfully!");
        } else {
            System.out.println("No rows were updated.");
        }
    } catch (SQLException e) {
        System.err.println("Error updating balance: " + e.getMessage());
    }
    
}
    
    
     public static String getUserName(String accno) {
    String query = "SELECT name FROM users WHERE acc_no = ?";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, accno);
        
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("name"); // Return the name if user exists
        }
    } catch (SQLException e) {
        System.err.println("Error authenticating user: " + e.getMessage());
    }
    return "Wrong Account Number!"; // Return null if user does not exist
}


    public static void insertTransaction(String tid, String category, double amount, String type, String acc_no, String to_acc, String y, String m, String d) {
    String query = "INSERT INTO tra (id, note, amount, type, from_acc, to_acc, year, month, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, tid);
        statement.setString(2, category);
        statement.setDouble(3, amount);
        statement.setString(4, type);
        statement.setString(5, acc_no);
        statement.setString(6, to_acc);
        statement.setString(7, y);
        statement.setString(8, m);
        statement.setString(9, d);
        statement.executeUpdate();
        System.out.println("Transaction inserted successfully!");
    } catch (SQLException e) {
        System.err.println("Error inserting transaction: " + e.getMessage());
    }
}
    
    public static void setBudget(String id, String acc_no, double amount, String y, String m, double i, double e) {
    String query = "INSERT INTO budget (id, acc_no, amount, year, month, t_income, t_expence) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, id);
        statement.setString(2, acc_no);
        statement.setDouble(3, amount);
        statement.setString(4, y);
        statement.setString(5, m);
        statement.setDouble(6, i);
        statement.setDouble(7, e);
        statement.executeUpdate();
        System.out.println("Transaction inserted successfully!");
    } catch (SQLException ex) {
        System.err.println("Error inserting transaction: " + ex.getMessage());
    }
}

    
    public static void updateBalance(String acc_no, double newBalance, int x) {
        double bal = getBalance(acc_no);
        if(x==0)
        {
            bal+=newBalance;
        }
        else if(x==1)
        {
            bal-=newBalance;
        }
    String updateQuery = "UPDATE users SET bal = ? WHERE acc_no = ?";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = connection.prepareStatement(updateQuery)) {
        // Set parameters for the update query
        statement.setDouble(1, bal);
        statement.setString(2, acc_no);
        // Execute the update query
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Balance updated successfully!");
        } else {
            System.out.println("No rows were updated.");
        }
    } catch (SQLException e) {
        System.err.println("Error updating balance: " + e.getMessage());
    }
    
}
    
    public static void updatetincomes(String id, double newBalance) {
        double bal = gettincomes(id);
        bal+=newBalance;
       
    String updateQuery = "UPDATE budget SET t_income = ? WHERE id = ?";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = connection.prepareStatement(updateQuery)) {
        // Set parameters for the update query
        statement.setDouble(1, bal);
        statement.setString(2, id);
        // Execute the update query
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Balance updated successfully!");
        } else {
            System.out.println("No rows were updated.");
        }
    } catch (SQLException e) {
        System.err.println("Error updating balance: " + e.getMessage());
    }
    
}
    
    
    public static void updatetexpences(String id, double newBalance) {
        double bal = gettexpences(id);
        bal+=newBalance;
       
    String updateQuery = "UPDATE budget SET t_expence = ? WHERE id = ?";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = connection.prepareStatement(updateQuery)) {
        // Set parameters for the update query
        statement.setDouble(1, bal);
        statement.setString(2, id);
        // Execute the update query
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Balance updated successfully!");
        } else {
            System.out.println("No rows were updated.");
        }
    } catch (SQLException e) {
        System.err.println("Error updating balance: " + e.getMessage());
    }
    
}
    
    
    


     public static void insertUser(int acc_no, String name, String nic, String dob, String email, String pwd, double balance) {
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         Statement statement = connection.createStatement()) {

        // SQL query to insert data into the database
        String sql = "INSERT INTO users (acc_no, name, nic, dob, email, pwd, bal) " +
             "VALUES ('" + acc_no + "', '" + name + "', '" + nic + "', '" + dob + "', '" + email + "', '" + pwd + "', '" + balance + "')";


        // Execute the insert statement
        int rowsInserted = statement.executeUpdate(sql);
        if (rowsInserted == 1) {
            System.out.println("User inserted successfully!");
        }
    } catch (SQLException e) {
        System.err.println("Error inserting user: " + e.getMessage());
    }
}

     public static double getBalance(String accno) {
    double balance = 0.0;
    String query = "SELECT bal FROM users WHERE acc_no = '" + accno + "'";
    
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        
        if (resultSet.next()) {
            balance = resultSet.getDouble("bal");
        }
    } catch (SQLException e) {
        System.err.println("Error retrieving balance: " + e.getMessage());
    }
    
    return balance;
}
     
     
     public static double gettincomes(String id) {
    double balance = 0.0;
    String query = "SELECT t_income FROM budget WHERE id = '" + id + "'";
    
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        
        if (resultSet.next()) {
            balance = resultSet.getDouble("t_income");
        }
    } catch (SQLException e) {
        System.err.println("Error retrieving balance: " + e.getMessage());
    }
    
    return balance;
}
     
     
     public static double gettexpences(String id) {
    double balance = 0.0;
    String query = "SELECT t_expence FROM budget WHERE id = '" + id + "'";
    
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        
        if (resultSet.next()) {
            balance = resultSet.getDouble("t_expence");
        }
    } catch (SQLException e) {
        System.err.println("Error retrieving balance: " + e.getMessage());
    }
    
    return balance;
}
     
     
     
     public static int getaccno(String nic) {
    int accno_ = 0 ;
    String query = "SELECT acc_no FROM users WHERE nic = '" + nic + "'";
    
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query)) {
        
        if (resultSet.next()) {
            accno_ = resultSet.getInt("acc_no");
        }
    } catch (SQLException e) {
        System.err.println("Error retrieving balance: " + e.getMessage());
    }
    
    return accno_;
}

     
     public static int getrownumbers(String table) {
        int rows = 0;
        String query = "SELECT COUNT(*) AS row_count FROM "+table;
        
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            if (resultSet.next()) {
                rows = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving balance: " + e.getMessage());
        }
        
        return rows;
    }
     
     public static List<List<Object>> getTransactions(String acc_no, String y, String m, String d, String typee) {
        List<List<Object>> transactions = new ArrayList<>();
//         System.out.println("SELECT id, note, amount, type, to_acc, datetime FROM tra WHERE from_acc = "+acc_no+" AND month = "+m+" AND date = "+d+" AND year = "+y+" AND type = "+typee);
        String query = "SELECT id, note, amount, type, to_acc, datetime FROM tra WHERE from_acc = "+acc_no+" AND month = '"+m+"' AND date = '"+d+"' AND year = '"+y+"' AND type = '"+typee+"'";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                List<Object> transaction = new ArrayList<>();
                // Retrieve values for each column
                String tid = resultSet.getString("id");
                String note = resultSet.getString("note");
                double amount = resultSet.getDouble("amount");
                String type = resultSet.getString("type");
                String to = resultSet.getString("to_acc");
                String dattime = resultSet.getString("datetime");
                
                // Add values to the transaction list
//                transaction.add(dattime);
                transaction.add(tid);
                transaction.add(note);
                transaction.add(amount);
//                transaction.add(to);
                
                // Add the transaction list to the list of transactions
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
        }
        return transactions;
    }
     
     public static Object[] getTransactionByAccount(String acc_no) {
    String query = "SELECT * FROM tra WHERE id = ? LIMIT 1";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, acc_no);
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Object[] row = new Object[numberOfColumns];
            for (int i = 0; i < numberOfColumns; i++) {
                row[i] = resultSet.getObject(i + 1);
            }
            return row; // Return the first row found
        }
    } catch (SQLException e) {
        System.err.println("Error retrieving transaction: " + e.getMessage());
    }
    return null; // Return null if no row is found
}


    
     
}

