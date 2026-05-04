package models;

public class Complaint {
    private int id;
    private String studentId;
    private String category;
    private String description;
    private String status;

    public Complaint(int id, String studentId, String category, String description, String status) {
        this.id = id;
        this.studentId = studentId;
        this.category = category;
        this.description = description;
        this.status = status;
    }

    public Complaint(String studentId, String category, String description, String status) {
        this.studentId = studentId;
        this.category = category;
        this.description = description;
        this.status = status;
    }

    // Standard Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}