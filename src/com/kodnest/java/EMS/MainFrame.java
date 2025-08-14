package com.kodnest.java.EMS;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Employee Management System");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        // Title
        JLabel title = new JLabel("Employee Management System");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.RED);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(title);

        // Grid panel for top 4 buttons
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        gridPanel.setOpaque(false);
        gridPanel.setMaximumSize(new Dimension(350, 100));

        JButton btn1 = createStyledButton("VIEW ALL EMPLOYEES");
        JButton btn2 = createStyledButton("VIEW EMPLOYEE");
        JButton btn3 = createStyledButton("ADD EMPLOYEE");
        JButton btn4 = createStyledButton("DELETE EMPLOYEE");

        gridPanel.add(btn1);
        gridPanel.add(btn2);
        gridPanel.add(btn3);
        gridPanel.add(btn4);

        mainPanel.add(gridPanel);

        // Update button
        JButton updateBtn = createStyledButton("UPDATE EMPLOYEE");
        updateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateBtn.setMaximumSize(new Dimension(200, 30));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(updateBtn);

        // Exit button
        JButton exitBtn = createStyledButton("EXIT");
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.setMaximumSize(new Dimension(200, 30));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(exitBtn);

        // Button actions
        btn1.addActionListener(e -> new ViewAllEmployeesFrame());
        btn2.addActionListener(e -> new ViewEmployeeFrame());
        btn3.addActionListener(e -> new AddEmployeeFrame());
        btn4.addActionListener(e -> {
            this.setVisible(false); // optional
            new DeleteEmployeeFrame(this);
        });
        updateBtn.addActionListener(e -> new UpdateEmployeeFrame());
        exitBtn.addActionListener(e -> System.exit(0)); // exit app

        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        return button;
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}