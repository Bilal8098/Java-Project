package org.yourcompany.yourproject.Bilal;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class HospitalManagementGUI {

    private JFrame frame;
    private JTextField nameField, passwordField, addressField, departmentField, ageField, phoneField, emailField, salaryField, medicalHistoryField, patientRoomField;
    private JPanel dynamicFieldsPanel;
    private JRadioButton adminRadio, doctorRadio, accountantRadio, nurseRadio, patientRadio;
    private ButtonGroup roleGroup;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HospitalManagementGUI::new);
    }

    public HospitalManagementGUI() {
        frame = new JFrame("Hospital Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        JPanel panel = new JPanel(new BorderLayout());

        // Role Selection Panel
        JPanel rolePanel = new JPanel();
        rolePanel.setLayout(new GridLayout(1, 5, 5, 5));

        adminRadio = new JRadioButton("Admin");
        doctorRadio = new JRadioButton("Doctor");
        accountantRadio = new JRadioButton("Accountant");
        nurseRadio = new JRadioButton("Nurse");
        patientRadio = new JRadioButton("Patient");

        roleGroup = new ButtonGroup();
        roleGroup.add(adminRadio);
        roleGroup.add(doctorRadio);
        roleGroup.add(accountantRadio);
        roleGroup.add(nurseRadio);
        roleGroup.add(patientRadio);

        rolePanel.add(adminRadio);
        rolePanel.add(doctorRadio);
        rolePanel.add(accountantRadio);
        rolePanel.add(nurseRadio);
        rolePanel.add(patientRadio);

        panel.add(rolePanel, BorderLayout.NORTH);

        // Dynamic Fields Panel
        dynamicFieldsPanel = new JPanel();
        dynamicFieldsPanel.setLayout(new GridLayout(10, 2, 5, 5));
        setupInputFields(dynamicFieldsPanel);
        panel.add(dynamicFieldsPanel, BorderLayout.CENTER);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> handleSubmit());
        panel.add(submitButton, BorderLayout.SOUTH);

        // Add Role Selection Listener
        addRoleSelectionListener();

        frame.add(panel);
        frame.setVisible(true);
    }

    private void setupInputFields(JPanel panel) {
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        panel.add(new JLabel("Age:"));
        ageField = new JTextField();
        panel.add(ageField);

        panel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        panel.add(phoneField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Salary:"));
        salaryField = new JTextField();
        panel.add(salaryField);

        panel.add(new JLabel("Department (For Doctor/Nurse):"));
        departmentField = new JTextField();
        panel.add(departmentField);

        panel.add(new JLabel("Medical History (For Patient):"));
        medicalHistoryField = new JTextField();
        panel.add(medicalHistoryField);

        panel.add(new JLabel("Room (For Patient):"));
        patientRoomField = new JTextField();
        panel.add(patientRoomField);

        toggleFieldsVisibility("Admin");
    }

    private void addRoleSelectionListener() {
        ActionListener listener = e -> {
            if (adminRadio.isSelected()) toggleFieldsVisibility("Admin");
            else if (doctorRadio.isSelected()) toggleFieldsVisibility("Doctor");
            else if (accountantRadio.isSelected()) toggleFieldsVisibility("Accountant");
            else if (nurseRadio.isSelected()) toggleFieldsVisibility("Nurse");
            else if (patientRadio.isSelected()) toggleFieldsVisibility("Patient");
        };

        adminRadio.addActionListener(listener);
        doctorRadio.addActionListener(listener);
        accountantRadio.addActionListener(listener);
        nurseRadio.addActionListener(listener);
        patientRadio.addActionListener(listener);
    }

    private void toggleFieldsVisibility(String role) {
        salaryField.setVisible(role.equals("Admin") || role.equals("Doctor") || role.equals("Accountant") || role.equals("Nurse"));
        departmentField.setVisible(role.equals("Doctor") || role.equals("Nurse"));
        medicalHistoryField.setVisible(role.equals("Patient"));
        patientRoomField.setVisible(role.equals("Patient"));
        dynamicFieldsPanel.revalidate();
        dynamicFieldsPanel.repaint();
    }

    private void handleSubmit() {
        String role = null;
        if (adminRadio.isSelected()) role = "Admin";
        else if (doctorRadio.isSelected()) role = "Doctor";
        else if (accountantRadio.isSelected()) role = "Accountant";
        else if (nurseRadio.isSelected()) role = "Nurse";
        else if (patientRadio.isSelected()) role = "Patient";

        if (role != null) addUser(role);
        else JOptionPane.showMessageDialog(frame, "Please select a role.", "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    private void addUser(String role) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/hospital",
                "hospitaladmin", "hospitaladmin")) {
            System.out.println("Connected to PostgreSQL Database!");
            String procedureCall;

            // Match the correct stored procedure
            switch (role) {
                case "Admin":
                    procedureCall = "CALL AddAdmin(?, ?, ?, ?, ?, ?)";
                    break;
                case "Doctor":
                    procedureCall = "{ CALL public.adddoctor(?, ?, ?, ?, ?, ?, ?) }";
                    break;
                case "Nurse":
                    procedureCall = "CALL public.addnurse(?, ?, ?, ?, ?, ?, ?, ?)";
                    break;
                case "Accountant":
                    procedureCall = "CALL AddAccountant(?, ?, ?, ?, ?, ?)";
                    break;
                case "Patient":
                    procedureCall = "CALL AddPatient(?, ?, ?, ?, ?, ?, ?, ?)";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid role: " + role);
            }

            try (PreparedStatement stmt = conn.prepareStatement(procedureCall)) {
                stmt.setString(1, nameField.getText());
                stmt.setString(2, addressField.getText());
                stmt.setString(3, phoneField.getText());
                stmt.setString(4, emailField.getText());
                stmt.setInt(5, Integer.parseInt(ageField.getText()));

                if ("Doctor".equals(role) || "Nurse".equals(role)) {
                    stmt.setInt(6, Integer.parseInt(departmentField.getText())); // Department ID
                    stmt.setString(7, salaryField.getText()); // Shift (for Nurse)
                    stmt.setBigDecimal(8, new java.math.BigDecimal(salaryField.getText())); // Salary as BigDecimal
                } else if ("Admin".equals(role) || "Accountant".equals(role)) {
                    stmt.setBigDecimal(6, new java.math.BigDecimal(salaryField.getText()));
                } else if ("Patient".equals(role)) {
                    stmt.setString(6, medicalHistoryField.getText());
                    stmt.setString(7, patientRoomField.getText());
                }
                stmt.execute();
                JOptionPane.showMessageDialog(frame, role + " added successfully.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "SQL Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid number format: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
