package com.kodnest.java.EMS;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdateEmployeeFrame extends JFrame {

	JTextField idField, nameField, salaryField, deptField, posField;
	JButton fetchBtn, updateBtn;

	public UpdateEmployeeFrame() {
		setTitle("Update Employee");
		setSize(450, 400);
		setLocationRelativeTo(null);
		setLayout(null);

		JLabel heading = new JLabel("Enter Employee ID to Update:");
		heading.setBounds(50, 20, 250, 25);
		add(heading);

		JLabel idLabel = new JLabel("ID:");
		idLabel.setBounds(50, 60, 100, 25);
		idField = new JTextField();
		idField.setBounds(150, 60, 200, 25);

		fetchBtn = new JButton("FETCH");
		fetchBtn.setBounds(150, 100, 100, 30);
		fetchBtn.addActionListener(e -> fetchEmployee());

		add(idLabel);
		add(idField);
		add(fetchBtn);

		// Labels and fields for employee details
		nameField = new JTextField();
		salaryField = new JTextField();
		deptField = new JTextField();
		posField = new JTextField();

		addLabelAndField("Name:", nameField, 140);
		addLabelAndField("Salary:", salaryField, 180);
		addLabelAndField("Department:", deptField, 220);
		addLabelAndField("Position:", posField, 260);

		updateBtn = new JButton("UPDATE");
		updateBtn.setBounds(100, 310, 100, 30);
		updateBtn.setEnabled(false);

		JButton backBtn = new JButton("BACK");
		backBtn.setBounds(220, 310, 100, 30);

		add(updateBtn);
		add(backBtn);

		updateBtn.addActionListener(e -> updateEmployee());
		backBtn.addActionListener(e -> dispose());

		setVisible(true);
	}

	private void addLabelAndField(String labelText, JTextField field, int y) {
		JLabel label = new JLabel(labelText);
		label.setBounds(50, y, 100, 25);
		field.setBounds(150, y, 200, 25);
		add(label);
		add(field);
	}

	private void fetchEmployee() {
		String idText = idField.getText().trim();
		if (idText.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter Employee ID.");
			return;
		}

		try {
			int id = Integer.parseInt(idText);
			Connection conn = DBConnection.getConnection();
			PreparedStatement pst = conn.prepareStatement("SELECT * FROM employee WHERE id = ?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				nameField.setText(rs.getString("name"));
				salaryField.setText(String.valueOf(rs.getInt("salary")));
				deptField.setText(rs.getString("department"));
				posField.setText(rs.getString("position"));
				updateBtn.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(this, "Employee not found.");
				updateBtn.setEnabled(false);
				clearFields();
			}

			conn.close();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "ID must be a number.");
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		}
	}

	private void updateEmployee() {
		try {
			int id = Integer.parseInt(idField.getText().trim());
			String name = nameField.getText().trim();
			int salary = Integer.parseInt(salaryField.getText().trim());
			String dept = deptField.getText().trim();
			String pos = posField.getText().trim();

			if (name.isEmpty() || dept.isEmpty() || pos.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please fill all fields.");
				return;
			}

			Connection conn = DBConnection.getConnection();
			PreparedStatement pst = conn
					.prepareStatement("UPDATE employee SET name=?, salary=?, department=?, position=? WHERE id=?");

			pst.setString(1, name);
			pst.setInt(2, salary);
			pst.setString(3, dept);
			pst.setString(4, pos);
			pst.setInt(5, id);

			int rows = pst.executeUpdate();
			if (rows > 0) {
				JOptionPane.showMessageDialog(this, "Employee updated successfully!");
				clearFields();
				updateBtn.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(this, "Update failed.");
			}

			conn.close();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Invalid input format.");
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		}
	}

	private void clearFields() {
		nameField.setText("");
		salaryField.setText("");
		deptField.setText("");
		posField.setText("");
	}
}
