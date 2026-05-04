package ui;

import dao.ComplaintDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class StudentForm extends JFrame {
    private JTextField txtStudentId;
    private JComboBox<String> cbCategory;
    private JTextArea txtDescription;
    private JButton btnSubmit;
    private ComplaintDAO dao;

    public StudentForm() {
        dao = new ComplaintDAO();
        setTitle("Submit a Complaint");
        setSize(500, 650); // Increased size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel with Padding
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.gridx = 0;

        // Header
        JLabel lblHeader = new JLabel("Complaint Registration", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridy = 0;
        mainPanel.add(lblHeader, gbc);

        // Student ID
        gbc.gridy++;
        mainPanel.add(new JLabel("Student Registration Number:"), gbc);
        gbc.gridy++;
        txtStudentId = new JTextField();
        txtStudentId.setPreferredSize(new Dimension(0, 35));
        txtStudentId.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(txtStudentId, gbc);

        // Category
        gbc.gridy++;
        mainPanel.add(new JLabel("Category:"), gbc);
        gbc.gridy++;
        String[] categories = {"Hostel", "Food", "Wi-Fi", "Academic", "Other"};
        cbCategory = new JComboBox<>(categories);
        cbCategory.setPreferredSize(new Dimension(0, 35));
        cbCategory.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
        cbCategory.setBackground(Color.WHITE);
        mainPanel.add(cbCategory, gbc);

        // Description
        gbc.gridy++;
        mainPanel.add(new JLabel("Description:"), gbc);
        gbc.gridy++;
        txtDescription = new JTextArea(12, 20); // Much bigger dialogue box
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(txtDescription);
        scrollPane.setPreferredSize(new Dimension(0, 200));
        
        // Modern Scrollbar (No Arrows, Progress Bar style)
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        
        mainPanel.add(scrollPane, gbc);

        // Submit Button
        gbc.gridy++;
        gbc.insets = new Insets(25, 5, 10, 5);
        btnSubmit = new JButton("Submit Complaint");
        btnSubmit.setBackground(new Color(25, 42, 86));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 16));
        btnSubmit.setPreferredSize(new Dimension(0, 45));
        btnSubmit.setFocusPainted(false);
        btnSubmit.setOpaque(true);
        btnSubmit.setBorderPainted(false);
        btnSubmit.addActionListener(e -> submitAction());
        mainPanel.add(btnSubmit, gbc);

        add(new JScrollPane(mainPanel)); // Allow the whole form to scroll if needed
    }

    private void submitAction() {
        String id = txtStudentId.getText().trim();
        String cat = (String) cbCategory.getSelectedItem();
        String desc = txtDescription.getText().trim();

        if (id.isEmpty() || desc.isEmpty()) {
            showInfoDialog("Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean success = dao.addComplaint(id, cat, desc);
        if (success) {
            showInfoDialog("Complaint Submitted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            txtStudentId.setText("");
            txtDescription.setText("");
        } else {
            showInfoDialog("Error submitting complaint.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showInfoDialog(String message, String title, int type) {
        JLabel label = new JLabel("<html><div style='width: 250px; text-align: center;'>" + message + "</div></html>");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        JOptionPane.showMessageDialog(this, label, title, type);
    }

    // Custom ScrollBar UI to remove arrows and look modern
    static class ModernScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(70, 130, 180, 150); // Semi-transparent Steel Blue
            this.trackColor = new Color(245, 245, 245);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            return button;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(thumbColor);
            g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, thumbBounds.width - 4, thumbBounds.height - 4, 10, 10);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        database.DatabaseSetup.createTables();
        SwingUtilities.invokeLater(() -> new StudentForm().setVisible(true));
    }
}