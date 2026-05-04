package ui;

import dao.ComplaintDAO;
import dao.UserDAO;
import models.Complaint;
import models.User;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final UserDAO userDAO;
    private final ComplaintDAO complaintDAO;
    private final Scanner scanner;
    private User currentUser;

    public ConsoleUI() {
        this.userDAO = new UserDAO();
        this.complaintDAO = new ComplaintDAO();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n=== Complaint Tracker System ===");
            System.out.println("1. Login");
            System.out.println("2. Register (Student)");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    login();
                    break;
                case "2":
                    register();
                    break;
                case "3":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User newUser = new User(username, password, "STUDENT");
        if (userDAO.registerUser(newUser)) {
            System.out.println("Registration successful! You can now login.");
        } else {
            System.out.println("Registration failed. Username might already exist.");
        }
    }

    private void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        currentUser = userDAO.authenticateUser(username, password);

        if (currentUser != null) {
            System.out.println("Login successful! Welcome, " + currentUser.getUsername());
            if (currentUser.getRole().equals("ADMIN")) {
                adminMenu();
            } else {
                studentMenu();
            }
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void studentMenu() {
        while (true) {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. Submit a new complaint");
            System.out.println("2. View my complaints");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    submitComplaint();
                    break;
                case "2":
                    viewMyComplaints();
                    break;
                case "3":
                    currentUser = null;
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void submitComplaint() {
        System.out.println("Select Category: ");
        System.out.println("1. Hostel");
        System.out.println("2. Food");
        System.out.println("3. Wi-Fi");
        System.out.println("4. Other");
        System.out.print("Choice: ");
        String catChoice = scanner.nextLine();
        
        String category = "Other";
        if (catChoice.equals("1")) category = "Hostel";
        else if (catChoice.equals("2")) category = "Food";
        else if (catChoice.equals("3")) category = "Wi-Fi";

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        Complaint complaint = new Complaint(String.valueOf(currentUser.getId()), category, description, "Pending");
        if (complaintDAO.addComplaint(complaint)) {
            System.out.println("Complaint submitted successfully.");
        } else {
            System.out.println("Failed to submit complaint.");
        }
    }

    private void viewMyComplaints() {
        List<Complaint> complaints = complaintDAO.getComplaintsByStudent(String.valueOf(currentUser.getId()));
        if (complaints.isEmpty()) {
            System.out.println("You have no complaints.");
            return;
        }

        System.out.println("\n--- My Complaints ---");
        for (Complaint c : complaints) {
            System.out.printf("ID: %d | Category: %s | Status: %s\n", c.getId(), c.getCategory(), c.getStatus());
            System.out.println("Description: " + c.getDescription());
            System.out.println("-------------------------");
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View all complaints");
            System.out.println("2. Update complaint status");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewAllComplaints();
                    break;
                case "2":
                    updateComplaintStatus();
                    break;
                case "3":
                    currentUser = null;
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void viewAllComplaints() {
        List<Complaint> complaints = complaintDAO.getAllComplaints();
        if (complaints.isEmpty()) {
            System.out.println("No complaints found.");
            return;
        }

        System.out.println("\n--- All Complaints ---");
        for (Complaint c : complaints) {
            System.out.printf("ID: %d | Student ID: %s | Category: %s | Status: %s\n", c.getId(), c.getStudentId(), c.getCategory(), c.getStatus());
            System.out.println("Description: " + c.getDescription());
            System.out.println("-------------------------");
        }
    }

    private void updateComplaintStatus() {
        System.out.print("Enter Complaint ID to update: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
            return;
        }

        System.out.println("Select New Status:");
        System.out.println("1. Pending");
        System.out.println("2. In Progress");
        System.out.println("3. Resolved");
        System.out.print("Choice: ");
        String statChoice = scanner.nextLine();
        
        String newStatus = "Pending";
        if (statChoice.equals("2")) newStatus = "In Progress";
        else if (statChoice.equals("3")) newStatus = "Resolved";

        if (complaintDAO.updateComplaintStatus(id, newStatus)) {
            System.out.println("Status updated successfully.");
        } else {
            System.out.println("Failed to update status. Please check the Complaint ID.");
        }
    }
}
