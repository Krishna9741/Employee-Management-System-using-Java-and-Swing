package com.kodnest.java.EMS;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewEmployeeFrame extends JFrame {

	JTextField idField;
	JLabel nameLabel, salaryLabel, deptLabel, posLabel;

	public ViewEmployeeFrame() {
		setTitle("View Employee");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setLayout(null);

		JLabel heading = new JLabel("Enter Employee ID");
		heading.setBounds(120, 20, 200, 25);
		add(heading);

		JLabel idLabel = new JLabel("ID:");
		idLabel.setBounds(50, 60, 80, 25);
		add(idLabel);

		idField = new JTextField();
		idField.setBounds(130, 60, 180, 25);
		add(idField);

		JButton submitBtn = new JButton("SUBMIT");
		submitBtn.setBounds(130, 100, 100, 30);
		add(submitBtn);

		// Employee detail labels
		nameLabel = new JLabel("Name: ");
		salaryLabel = new JLabel("Salary: ");
		deptLabel = new JLabel("Department: ");
		posLabel = new JLabel("Position: ");

		nameLabel.setBounds(50, 140, 300, 20);
		salaryLabel.setBounds(50, 160, 300, 20);
		deptLabel.setBounds(50, 180, 300, 20);
		posLabel.setBounds(50, 200, 300, 20);

		add(nameLabel);
		add(salaryLabel);
		add(deptLabel);
		add(posLabel);

		JButton backBtn = new JButton("BACK");
		backBtn.setBounds(250, 230, 100, 30);
		add(backBtn);

		// Actions
		submitBtn.addActionListener(e -> fetchEmployee());
		backBtn.addActionListener(e -> dispose());

		setVisible(true);
	}

	private void fetchEmployee() {
		String idText = idField.getText().trim();
		if (idText.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter an Employee ID.");
			return;
		}

		try {
			int id = Integer.parseInt(idText);
			Connection conn = DBConnection.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM employee WHERE id = ?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				nameLabel.setText("Name: " + rs.getString("name"));
				salaryLabel.setText("Salary: " + rs.getInt("salary"));
				deptLabel.setText("Department: " + rs.getString("department"));
				posLabel.setText("Position: " + rs.getString("position"));
			} else {
				JOptionPane.showMessageDialog(this, "Employee not found.");
				nameLabel.setText("Name: ");
				salaryLabel.setText("Salary: ");
				deptLabel.setText("Department: ");
				posLabel.setText("Position: ");
			}

			conn.close();

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "ID must be a number.");
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		}
	}
}
