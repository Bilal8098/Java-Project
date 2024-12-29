package org.yourcompany.yourproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddPatient extends JFrame {

    private JTextField patientNameField, patientAgeField, departmentHistoryField;
    private JButton submitButton;

    public AddPatient() {
        // Frame settings
        setTitle("Add New Patient");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the frame

        // Create labels and text fields
        JLabel nameLabel = new JLabel("Patient Name:");
        patientNameField = new JTextField(20);

        JLabel ageLabel = new JLabel("Patient Age:");
        patientAgeField = new JTextField(20);

        JLabel deptLabel = new JLabel("Department History:");
        departmentHistoryField = new JTextField(20);

        submitButton = new JButton("Submit");

        // Layout setup
        setLayout(new FlowLayout());
        add(nameLabel);
        add(patientNameField);
        add(ageLabel);
        add(patientAgeField);
        add(deptLabel);
        add(departmentHistoryField);
        add(submitButton);

        // Submit button action listener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPatient();
            }
        });
    }

    private void addPatient() {
        // Get input data from text fields
        String patientName = patientNameField.getText();
        String patientAgeText = patientAgeField.getText();
        String departmentHistoryText = departmentHistoryField.getText();

        // Validate inputs
        if (patientName.isEmpty() || patientAgeText.isEmpty() || departmentHistoryText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int patientAge;
        int departmentHistory;

        try {
            patientAge = Integer.parseInt(patientAgeText);
            departmentHistory = Integer.parseInt(departmentHistoryText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age and Department History must be valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database connection parameters
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";

        // SQL query to call the procedure
        String sql = "CALL AddPatient(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the procedure call
            stmt.setString(1, patientName);
            stmt.setInt(2, patientAge);
            stmt.setInt(3, departmentHistory);

            // Execute the procedure
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Patient added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear fields after submission
            patientNameField.setText("");
            patientAgeField.setText("");
            departmentHistoryField.setText("");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding patient: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Run the GUI application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddPatient().setVisible(true);
            }
        });
    }
}
