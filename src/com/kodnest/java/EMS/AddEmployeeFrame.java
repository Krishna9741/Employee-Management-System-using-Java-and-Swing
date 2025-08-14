package com.kodnest.java.EMS;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddEmployeeFrame extends JFrame {

	JTextField nameField, salaryField, deptField, posField;

	public AddEmployeeFrame() {
		setTitle("Add Employee");
		setSize(400, 350);
		setLocationRelativeTo(null);
		setLayout(null);

		// Labels and fields
		JLabel nameLabel = new JLabel("Name:");
		JLabel salaryLabel = new JLabel("Salary:");
		JLabel deptLabel = new JLabel("Department:");
		JLabel posLabel = new JLabel("Position:");

		nameField = new JTextField();
		salaryField = new JTextField();
		deptField = new JTextField();
		posField = new JTextField();

		nameLabel.setBounds(50, 40, 100, 25);
		nameField.setBounds(150, 40, 180, 25);

		salaryLabel.setBounds(50, 80, 100, 25);
		salaryField.setBounds(150, 80, 180, 25);

		deptLabel.setBounds(50, 120, 100, 25);
		deptField.setBounds(150, 120, 180, 25);

		posLabel.setBounds(50, 160, 100, 25);
		posField.setBounds(150, 160, 180, 25);

		add(nameLabel);
		add(nameField);
		add(salaryLabel);
		add(salaryField);
		add(deptLabel);
		add(deptField);
		add(posLabel);
		add(posField);

		// Buttons
		JButton addBtn = new JButton("ADD EMPLOYEE");
		JButton backBtn = new JButton("BACK");

		addBtn.setBounds(80, 210, 150, 30);
		backBtn.setBounds(240, 210, 80, 30);

		add(addBtn);
		add(backBtn);

		addBtn.addActionListener(e -> addEmployee());
		backBtn.addActionListener(e -> dispose());

		setVisible(true);
	}

	private void addEmployee() {
		String name = nameField.getText().trim();
		String salaryStr = salaryField.getText().trim();
		String dept = deptField.getText().trim();
		String pos = posField.getText().trim();

		if (name.isEmpty() || salaryStr.isEmpty() || dept.isEmpty() || pos.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please fill all fields.");
			return;
		}

		try {
			int salary = Integer.parseInt(salaryStr);
			Connection conn = DBConnection.getConnection();
			PreparedStatement pst = conn
					.prepareStatement("INSERT INTO employee(name, salary, department, position) VALUES (?, ?, ?, ?)");

			pst.setString(1, name);
			pst.setInt(2, salary);
			pst.setString(3, dept);
			pst.setString(4, pos);

			int rows = pst.executeUpdate();
			if (rows > 0) {
				JOptionPane.showMessageDialog(this, "Employee added successfully!");
				nameField.setText("");
				salaryField.setText("");
				deptField.setText("");
				posField.setText("");
			} else {
				JOptionPane.showMessageDialog(this, "Failed to add employee.");
			}

			conn.close();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Salary must be a number.");
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
		}
	}
}
