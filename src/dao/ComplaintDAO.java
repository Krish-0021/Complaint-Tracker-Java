package dao;

import database.DBConnection;
import models.Complaint;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {

    // 1. Method for Students to submit a new complaint
    public boolean addComplaint(String studentId, String category, String description) {
        String sql = "INSERT INTO complaints(student_id, category, description, status) VALUES(?,?,?, 'Pending')";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, studentId);
            pstmt.setString(2, category);
            pstmt.setString(3, description);
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Overloaded method to accept a Complaint object
    public boolean addComplaint(Complaint complaint) {
        return addComplaint(complaint.getStudentId(), complaint.getCategory(), complaint.getDescription());
    }

    // 2. Method for Students to view their own complaints
    public List<Complaint> getComplaintsByStudent(String studentId) {
        List<Complaint> list = new ArrayList<>();
        String sql = "SELECT * FROM complaints WHERE student_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                list.add(new Complaint(
                    rs.getInt("id"),
                    rs.getString("student_id"),
                    rs.getString("category"),
                    rs.getString("description"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. Method for Admin to view all complaints
    public List<Complaint> getAllComplaints() {
        List<Complaint> list = new ArrayList<>();
        String sql = "SELECT * FROM complaints";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new Complaint(
                    rs.getInt("id"),
                    rs.getString("student_id"),
                    rs.getString("category"),
                    rs.getString("description"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 4. Method for Admin to update status
    public boolean updateComplaintStatus(int id, String newStatus) {
        // This is a classic JDBC UPDATE query
        String sql = "UPDATE complaints SET status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newStatus);
            pstmt.setInt(2, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Returns true if a row was actually updated

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatus(int id, String newStatus) {
        String sql = "UPDATE complaints SET status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newStatus);
            pstmt.setInt(2, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. Method to delete a complaint
    public boolean deleteComplaint(int id) {
        String sql = "DELETE FROM complaints WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}