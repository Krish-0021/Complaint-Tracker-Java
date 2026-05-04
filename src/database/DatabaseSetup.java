package database;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {
    public static void createTables() {
        String sql = "CREATE TABLE IF NOT EXISTS complaints (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "student_id TEXT NOT NULL," +
                "category TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "status TEXT DEFAULT 'Pending'" +
                ");";

        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Complaint table is ready!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createTables();
    }
}