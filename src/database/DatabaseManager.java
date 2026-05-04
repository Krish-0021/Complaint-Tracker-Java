package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    // MySQL configuration placeholders - Update these with your actual credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/complaint_tracker_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = "password";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Add it to your classpath.");
            throw new SQLException(e);
        }
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Create Users Table (MySQL syntax: AUTO_INCREMENT)
            String sqlUsers = "CREATE TABLE IF NOT EXISTS users (" +
                              "id INT AUTO_INCREMENT PRIMARY KEY," +
                              "username VARCHAR(50) UNIQUE NOT NULL," +
                              "password VARCHAR(100) NOT NULL," +
                              "role VARCHAR(20) NOT NULL" +
                              ");";
            stmt.execute(sqlUsers);

            // Create Complaints Table
            String sqlComplaints = "CREATE TABLE IF NOT EXISTS complaints (" +
                                   "id INT AUTO_INCREMENT PRIMARY KEY," +
                                   "student_id VARCHAR(50) NOT NULL," +
                                   "category VARCHAR(100) NOT NULL," +
                                   "description TEXT NOT NULL," +
                                   "status VARCHAR(20) NOT NULL" +
                                   ");";
            stmt.execute(sqlComplaints);
            
            // Create a default admin if not exists (MySQL syntax: INSERT IGNORE)
            String sqlAdmin = "INSERT IGNORE INTO users (username, password, role) VALUES ('admin', 'admin', 'ADMIN');";
            stmt.execute(sqlAdmin);

        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }
}

