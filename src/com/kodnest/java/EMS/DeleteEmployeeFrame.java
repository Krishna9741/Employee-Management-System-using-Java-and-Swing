package com.kodnest.java.EMS;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteEmployeeFrame extends JFrame {

    private JTextField idField;
    private JTextArea resultArea;
    private JButton deleteBtn;
    private JFrame mainFrame; // Reference to the calling MainFrame

    public DeleteEmployeeFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        setTitle("Delete Employee");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ───── Top Panel (Search) ─────
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(Color.BLACK);

        JLabel label = new JLabel("Enter Employee ID to delete:");
        label.setForeground(Color.WHITE);
        topPanel.add(label);

        idField = new JTextField(10);
        topPanel.add(idField);

        JButton searchBtn = new JButton("SEARCH");
        topPanel.add(searchBtn);

        add(topPanel, BorderLayout.NORTH);

        // ───── Center Panel (Result Display) ─────
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        // ───── Bottom Panel (Back on Left, Delete on Right) ─────
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.BLACK);

        // Left side (BACK button)
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(Color.BLACK);
        JButton backBtn = new JButton("BACK");
        backBtn.setBackground(Color.GRAY);
        backBtn.setForeground(Color.WHITE);
        leftPanel.add(backBtn);

        // Right side (DELETE button)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.BLACK);
        deleteBtn = new JButton("DELETE");
        deleteBtn.setBackground(Color.RED);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setEnabled(false);
        rightPanel.add(deleteBtn);

        // Add subpanels to bottomPanel
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        // ───── Event Listeners ─────
        searchBtn.addActionListener(e -> searchEmployee());
        deleteBtn.addActionListener(e -> deleteEmployee());
        backBtn.addActionListener(e -> {
            this.dispose();                 // close current frame
            mainFrame.setVisible(true);    // show previous frame again
        });

        setVisible(true);
    }

    private void searchEmployee() {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ID.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM employee WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String data = String.format(
                    "ID: %d\nName: %s\nSalary: %d\nDepartment: %s\nPosition: %s",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("salary"),
                    rs.getString("department"),
                    rs.getString("position")
                );
                resultArea.setText(data);
                deleteBtn.setEnabled(true);
            } else {
                resultArea.setText("No employee found with ID: " + id);
                deleteBtn.setEnabled(false);
            }

            conn.close();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID must be a number.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void deleteEmployee() {
        String idText = idField.getText().trim();
        int id = Integer.parseInt(idText);

        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this employee?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            try {
                Connection conn = DBConnection.getConnection();

                // Optional: Archive before deletion
                PreparedStatement archiveStmt = conn.prepareStatement(
                    "INSERT INTO deleted_employees SELECT * FROM employee WHERE id = ?"
                );
                archiveStmt.setInt(1, id);
                archiveStmt.executeUpdate();

                PreparedStatement stmt = conn.prepareStatement("DELETE FROM employee WHERE id = ?");
                stmt.setInt(1, id);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully.");
                    resultArea.setText("");
                    idField.setText("");
                    deleteBtn.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Deletion failed.");
                }

                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    // (Optional) Standalone test
    public static void main(String[] args) {
        JFrame dummy = new JFrame(); // for testing only
        dummy.setVisible(false);
        new DeleteEmployeeFrame(dummy);
    }
}

