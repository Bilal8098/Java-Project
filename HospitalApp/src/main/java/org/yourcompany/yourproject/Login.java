package org.yourcompany.yourproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

class Login {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Login");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout
        frame.setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 102)); // Dark teal color
        JLabel headerLabel = new JLabel("Welcome to the Hospital System", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(5, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));
        loginPanel.setBackground(new Color(224, 255, 255)); // Light cyan color

        JLabel idLabel = new JLabel("Enter ID:");
        idLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField idField = new JTextField();

        JLabel roleLabel = new JLabel("Select Role:");
        roleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Role selection using radio buttons
        JRadioButton doctorButton = new JRadioButton("Doctor");
        JRadioButton nurseButton = new JRadioButton("Nurse");
        JRadioButton accountantButton = new JRadioButton("Accountant");
        JRadioButton adminButton = new JRadioButton("Admin");

        // Group radio buttons
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(doctorButton);
        roleGroup.add(nurseButton);
        roleGroup.add(accountantButton);
        roleGroup.add(adminButton);

        JPanel rolePanel = new JPanel();
        rolePanel.setLayout(new GridLayout(1, 4, 5, 5));
        rolePanel.setBackground(new Color(224, 255, 255));
        rolePanel.add(doctorButton);
        rolePanel.add(nurseButton);
        rolePanel.add(accountantButton);
        rolePanel.add(adminButton);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 153, 153));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));

        loginPanel.add(idLabel);
        loginPanel.add(idField);
        loginPanel.add(roleLabel);
        loginPanel.add(rolePanel);
        loginPanel.add(new JLabel()); // Empty label for spacing
        loginPanel.add(loginButton);

        frame.add(loginPanel, BorderLayout.CENTER);

        // Action listener for login button
  // Action listener for login button
loginButton.addActionListener(e -> {
    String idNumber = idField.getText().trim(); // Use idNumber instead of username
    String selectedRole = null;

    if (doctorButton.isSelected()) selectedRole = "Doctor";
    else if (nurseButton.isSelected()) selectedRole = "Nurse";
    else if (accountantButton.isSelected()) selectedRole = "Accountant";
    else if (adminButton.isSelected()) selectedRole = "Admin";

    if (idNumber.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (selectedRole == null) {
        JOptionPane.showMessageDialog(frame, "Please select a role!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Database verification
    try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hospital", "hospitaladmin", "hospitaladmin")) {
        System.out.println("Connected to the Database");
        String query = "SELECT userrole FROM hospitalusers WHERE userid = ? AND userrole = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(idNumber)); // Convert ID number to integer
            stmt.setString(2, selectedRole);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Check if the role is Nurse
                    if (selectedRole.equals("Nurse")) {
                        NurseUI.main(new String[]{});
                    } else if (selectedRole.equals("Doctor")) {
                        doctorUI.main(new String[]{});
                    }
                    else if (selectedRole.equals("Accountant")) {
                        AccountantGUI.main(new String[]{});
                    }
                    else if(selectedRole.equals("Admin")){
                        AdminUi.main(new String[]{});
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid ID or Role!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(frame, "ID must be a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
    }
});



        // Make the frame visible
        frame.setVisible(true);
    }
}
