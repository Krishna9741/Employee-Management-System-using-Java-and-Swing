package com.kodnest.java.EMS;

import java.sql.*;

public class DBConnection {
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL Driver
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found.");
			e.printStackTrace();
		}

		String url = "jdbc:mysql://localhost:3306/employees"; // Replace with your DB name
		String user = "root"; // Replace with your MySQL username
		String password = "1318"; // Replace with your MySQL password

		return DriverManager.getConnection(url, user, password);
	}
}
