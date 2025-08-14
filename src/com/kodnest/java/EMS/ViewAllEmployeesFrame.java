package com.kodnest.java.EMS;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class ViewAllEmployeesFrame extends JFrame {

	private JTable table;
	private DefaultTableModel model;

	public ViewAllEmployeesFrame() {
		setTitle("All Employees");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		model = new DefaultTableModel();
		table = new JTable(model);

		// Add columns
		model.addColumn("ID");
		model.addColumn("Name");
		model.addColumn("Salary");
		model.addColumn("Department");
		model.addColumn("Position");

		// Load data from DB
		loadData();

		// Table header style
		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.DARK_GRAY);
		header.setForeground(Color.WHITE);
		header.setFont(new Font("Arial", Font.BOLD, 14));

		// Row and font styling
		table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		table.setRowHeight(25);
		table.setGridColor(Color.GRAY);

		// Zebra striping renderer
		table.setDefaultRenderer(Object.class, new ZebraRenderer());

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		// Back button
		JButton backBtn = new JButton("BACK");
		backBtn.setBackground(Color.DARK_GRAY);
		backBtn.setForeground(Color.WHITE);
		backBtn.setFocusPainted(false);
		backBtn.setFont(new Font("Arial", Font.PLAIN, 12));
		backBtn.addActionListener(e -> dispose());

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.BLACK);
		bottomPanel.add(backBtn);
		add(bottomPanel, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void loadData() {
		try {
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM employee");

			while (rs.next()) {
				model.addRow(new Object[] { rs.getInt("id"), rs.getString("name"), rs.getInt("salary"),
						rs.getString("department"), rs.getString("position") });
			}

			conn.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		}
	}

	// Zebra renderer: alternate row colors
	private class ZebraRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int col) {

			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

			if (!isSelected) {
				if (row % 2 == 0) {
					c.setBackground(new Color(245, 245, 245)); // Light gray
				} else {
					c.setBackground(Color.WHITE);
				}
				c.setForeground(Color.BLACK);
			}

			return c;
		}
	}
}
