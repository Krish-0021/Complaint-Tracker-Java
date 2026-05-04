package ui;

import database.DatabaseSetup;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class App extends JFrame {

    public App() {
        setTitle("Grievance Redressal System - Home");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.gridx = 0;

        // Header
        JLabel lblHeader = new JLabel("Complaint Tracker System", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 26));
        gbc.gridy = 0;
        mainPanel.add(lblHeader, gbc);

        // Subtitle
        JLabel lblSub = new JLabel("Select your portal to continue", SwingConstants.CENTER);
        lblSub.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 40, 10);
        mainPanel.add(lblSub, gbc);

        // Student Portal Button
        JButton btnStudent = new JButton("Student Portal");
        btnStudent.setFont(new Font("Arial", Font.BOLD, 18));
        btnStudent.setPreferredSize(new Dimension(0, 60));
        btnStudent.setBackground(new Color(25, 42, 86));
        btnStudent.setForeground(Color.WHITE);
        btnStudent.setFocusPainted(false);
        btnStudent.setOpaque(true);
        btnStudent.setBorderPainted(false);
        btnStudent.addActionListener(e -> new StudentForm().setVisible(true));
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(btnStudent, gbc);

        // Admin Portal Button
        JButton btnAdmin = new JButton("Admin Dashboard");
        btnAdmin.setFont(new Font("Arial", Font.BOLD, 18));
        btnAdmin.setPreferredSize(new Dimension(0, 60));
        btnAdmin.setBackground(new Color(30, 81, 40));
        btnAdmin.setForeground(Color.WHITE);
        btnAdmin.setFocusPainted(false);
        btnAdmin.setOpaque(true);
        btnAdmin.setBorderPainted(false);
        btnAdmin.addActionListener(e -> new AdminDashboard().setVisible(true));
        gbc.gridy = 3;
        mainPanel.add(btnAdmin, gbc);



        // Use a JScrollPane for the main panel with modern scrollbar
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane);
    }

    // Static Modern ScrollBar UI for consistency
    static class ModernScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(70, 130, 180, 150);
            this.trackColor = new Color(245, 245, 245);
        }
        @Override protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }
        @Override protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }
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
        DatabaseSetup.createTables();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}