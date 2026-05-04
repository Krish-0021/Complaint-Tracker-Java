package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Since complaints.db is in your root folder, we use this relative path
    private static final String URL = "jdbc:sqlite:complaints.db";

    public static Connection getConnection() {
        try {
            // This loads the SQLite driver you already have in your lib folder
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check if the .db file exists.");
            e.printStackTrace();
            return null;
        }
    }
}