package ui;

import dao.ComplaintDAO;
import models.Complaint;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private ComplaintDAO dao;

    public AdminDashboard() {
        dao = new ComplaintDAO();
        setTitle("Admin Dashboard");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        JLabel lblHeader = new JLabel("Complaint Management System", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 24));
        lblHeader.setBorder(new EmptyBorder(10, 0, 20, 0));
        mainPanel.add(lblHeader, BorderLayout.NORTH);

        // Table Setup
        String[] columnNames = { "ID", "Student Registration Number", "Category", "Description", "Status" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 15));
        header.setBackground(new Color(240, 240, 240));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        JScrollPane scrollPane = new JScrollPane(table);
        // Modern Scrollbar
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Control Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnRefresh = new JButton("Refresh Data");
        btnRefresh.setPreferredSize(new Dimension(140, 40));
        btnRefresh.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton btnResolve = new JButton("Mark as Resolved");
        btnResolve.setPreferredSize(new Dimension(180, 40));
        btnResolve.setBackground(new Color(30, 81, 40));
        btnResolve.setForeground(Color.WHITE);
        btnResolve.setFont(new Font("Arial", Font.BOLD, 14));
        btnResolve.setFocusPainted(false);
        btnResolve.setOpaque(true);
        btnResolve.setBorderPainted(false);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setPreferredSize(new Dimension(100, 40));
        btnDelete.setBackground(new Color(220, 53, 69));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Arial", Font.BOLD, 14));
        btnDelete.setFocusPainted(false);
        btnDelete.setOpaque(true);
        btnDelete.setBorderPainted(false);

        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnResolve);
        buttonPanel.add(btnDelete);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        btnRefresh.addActionListener(e -> loadData());
        btnResolve.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                if (dao.updateStatus(id, "Resolved")) {
                    showInfoDialog("Complaint #" + id + " has been successfully marked as Resolved!",
                            "Update Successful", JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                } else {
                    showInfoDialog("Failed to update status.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                showInfoDialog("Please select a row from the table first.", "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete Complaint #" + id + "?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (dao.deleteComplaint(id)) {
                        showInfoDialog("Complaint #" + id + " has been successfully deleted!",
                                "Delete Successful", JOptionPane.INFORMATION_MESSAGE);
                        loadData();
                    } else {
                        showInfoDialog("Failed to delete complaint.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                showInfoDialog("Please select a row from the table first.", "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Complaint> complaints = dao.getAllComplaints();
        for (Complaint c : complaints) {
            tableModel.addRow(
                    new Object[] { c.getId(), c.getStudentId(), c.getCategory(), c.getDescription(), c.getStatus() });
        }
    }

    private void showInfoDialog(String message, String title, int type) {
        JLabel label = new JLabel("<html><div style='width: 300px; text-align: center;'>" + message + "</div></html>");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        JOptionPane.showMessageDialog(this, label, title, type);
    }

    // Shared Modern ScrollBar UI
    static class ModernScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(70, 130, 180, 180);
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
            g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2, thumbBounds.width - 4, thumbBounds.height - 4, 10,
                    10);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        database.DatabaseSetup.createTables();
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}