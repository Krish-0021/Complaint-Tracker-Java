import database.DBConnection;
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            System.out.println("✅ Success! Antigravity is connected to complaints.db");
            try {
                conn.close();
            } catch (Exception e) {
            }
        } else {
            System.out.println("❌ Connection failed. Check your lib folder for the SQLite JAR.");
        }
    }
}