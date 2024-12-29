package org.yourcompany.yourproject;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class doctorUI {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Doctor Dashboard");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout
        frame.setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 102)); // Dark teal color
        JLabel headerLabel = new JLabel("Doctor Dashboard", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 1, 10, 10)); // Updated grid to include new buttons
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        menuPanel.setBackground(new Color(224, 255, 255)); // Light cyan color

        // Buttons for different functionalities
        JButton appointmentButton = new JButton("Appointment");
        JButton departmentsButton = new JButton("Departments");
        JButton presciptionButton = new JButton("presciption");
        JButton addAppointmentButton = new JButton("Add Appointment");
        JButton addpresciptionButton = new JButton("Add presciption");
        JButton addDepartmentsButton = new JButton("Add Departments");
        JButton addPatientButton = new JButton("Add Patient");
        JButton reviewPatientsButton = new JButton("Review Patients");

        // Style buttons
        JButton[] buttons = {appointmentButton, departmentsButton, presciptionButton, addAppointmentButton, addpresciptionButton, addDepartmentsButton, addPatientButton, reviewPatientsButton};
        for (JButton button : buttons) {
            button.setBackground(new Color(0, 153, 153)); // Teal color
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 14));
        }

        // Add buttons to menu panel
        menuPanel.add(appointmentButton);
        menuPanel.add(departmentsButton);
        menuPanel.add(presciptionButton);
        menuPanel.add(addAppointmentButton);
        menuPanel.add(addpresciptionButton);
        menuPanel.add(addDepartmentsButton);
        menuPanel.add(addPatientButton);
        menuPanel.add(reviewPatientsButton);

        frame.add(menuPanel, BorderLayout.WEST);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        contentPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        // Placeholder panels for each functionality
        JPanel appointmentPanel = new JPanel();
        appointmentPanel.setBackground(new Color(245, 245, 245));

        // Fetch appointment from the database and display in a table
        String[] appointmentColumns = {"ID", "Patient ID", "Appointment Date", "Doctor ID"};
        JTable appointmentTable = new JTable();
        DefaultTableModel appointmentTableModel = new DefaultTableModel(new Object[][]{}, appointmentColumns);
        appointmentTable.setModel(appointmentTableModel);

        // Fetch data from database and populate the table
        loadAppointmentData(appointmentTableModel);

        JScrollPane appointmentScrollPane = new JScrollPane(appointmentTable);
        appointmentPanel.add(appointmentScrollPane);

        JPanel presciptionsPanel = new JPanel();
        presciptionsPanel.setBackground(new Color(245, 245, 245));

        // Fetch presciption from the database and display in a table
        String[] presciptionColumns = {"ID", "Patient ID", "Medications"};
        JTable presciptionTable = new JTable();
        DefaultTableModel presciptionTableModel = new DefaultTableModel(new Object[][]{}, presciptionColumns);
        presciptionTable.setModel(presciptionTableModel);

        // Fetch data from database and populate the table
        loadpresciptionsData(presciptionTableModel);

        JScrollPane presciptionScrollPane = new JScrollPane(presciptionTable);
        presciptionsPanel .add(presciptionScrollPane);

        // Departments Panel with Add and Show Functionality
        JPanel departmentsPanel = new JPanel();
        departmentsPanel.setLayout(new BorderLayout());

        // Sub-panels for departments
        JPanel departmentsTopPanel = new JPanel();
        departmentsTopPanel.setBackground(new Color(224, 255, 255)); // Light cyan

        // Table to display departments data
        String[] departmentColumns = {"ID", "Department Name", "Staff Count"};
        JTable departmentTable = new JTable();
        DefaultTableModel departmentTableModel = new DefaultTableModel(new Object[][]{}, departmentColumns);
        departmentTable.setModel(departmentTableModel);

        // Fetch departments data from the database
        loadDepartmentsData(departmentTableModel);

        JScrollPane departmentScrollPane = new JScrollPane(departmentTable);
        departmentsTopPanel.add(new JLabel("Departments"));
        departmentsPanel.add(departmentsTopPanel, BorderLayout.NORTH);
        departmentsPanel.add(departmentScrollPane, BorderLayout.CENTER);

        // Add panels to the main content area
        contentPanel.add(appointmentPanel, "appointment");
        contentPanel.add(departmentsPanel, "Departments");
        contentPanel.add(presciptionsPanel, "presciption");

        frame.add(contentPanel, BorderLayout.CENTER);

        // Action listeners for main menu buttons
        CardLayout cl = (CardLayout) (contentPanel.getLayout());

        appointmentButton.addActionListener(e -> cl.show(contentPanel, "appointment"));
        departmentsButton.addActionListener(e -> cl.show(contentPanel, "Departments"));
        presciptionButton.addActionListener(e -> cl.show(contentPanel, "presciption"));

        // Action listener for "Add Appointment"
        addAppointmentButton.addActionListener(e -> {
            JPanel addAppointmentPanel = new JPanel();
            addAppointmentPanel.setBackground(new Color(245, 245, 245));

            JTextField patientIDField = new JTextField(15);
            JTextField doctorIDField = new JTextField(15);
            JTextField dateOfAppointmentField = new JTextField(15);

            JButton submitButton = new JButton("Add Appointment");

            // Layout for adding an appointment
            addAppointmentPanel.setLayout(new GridLayout(4, 2));
            addAppointmentPanel.add(new JLabel("Patient ID:"));
            addAppointmentPanel.add(patientIDField);
            addAppointmentPanel.add(new JLabel("Doctor ID:"));
            addAppointmentPanel.add(doctorIDField);
            addAppointmentPanel.add(new JLabel("Appointment Date:"));
            addAppointmentPanel.add(dateOfAppointmentField);
            addAppointmentPanel.add(submitButton);

            // Add the add appointment panel to the content
            contentPanel.add(addAppointmentPanel, "Add Appointment");

            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Insert data into the database
                    addAppointmentToDatabase(patientIDField.getText(), doctorIDField.getText(), dateOfAppointmentField.getText());
                    // Refresh the appointment table
                    loadAppointmentData(appointmentTableModel);
                    cl.show(contentPanel, "appointment");
                }
            });

            cl.show(contentPanel, "Add Appointment");
        });

        // Action listener for "Add presciption"
        addpresciptionButton.addActionListener(e -> {
            JPanel addpresciptionPanel = new JPanel();
            addpresciptionPanel.setBackground(new Color(245, 245, 245));

            JTextField patientIDField = new JTextField(15);
            JTextField medicationsField = new JTextField(15);

            JButton submitButton = new JButton("Add presciption");

            // Layout for adding a presciption
            addpresciptionPanel.setLayout(new GridLayout(3, 2));
            addpresciptionPanel.add(new JLabel("Patient ID:"));
            addpresciptionPanel.add(patientIDField);
            addpresciptionPanel.add(new JLabel("Medications:"));
            addpresciptionPanel.add(medicationsField);
            addpresciptionPanel.add(submitButton);

            // Add the add presciption panel to the content
            contentPanel.add(addpresciptionPanel, "Add presciption");

            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Insert data into the database
                    addpresciptionToDatabase(patientIDField.getText(), medicationsField.getText());
                    // Refresh the presciption table
                    loadpresciptionsData(presciptionTableModel);
                    cl.show(contentPanel, "presciption");
                }
            });

            cl.show(contentPanel, "Add presciption");
        });

        // Action listener for "Add Departments"
        addDepartmentsButton.addActionListener(e -> {
            JPanel addDepartmentsPanel = new JPanel();
            addDepartmentsPanel.setBackground(new Color(245, 245, 245));

            JTextField deptNameField = new JTextField(15);
            JTextField numberOfStaffField = new JTextField(15);

            JButton submitButton = new JButton("Add Department");

            // Layout for adding departments
            addDepartmentsPanel.setLayout(new GridLayout(3, 2));
            addDepartmentsPanel.add(new JLabel("Department Name:"));
            addDepartmentsPanel.add(deptNameField);
            addDepartmentsPanel.add(new JLabel("Staff Count:"));
            addDepartmentsPanel.add(numberOfStaffField);
            addDepartmentsPanel.add(submitButton);

            // Add the add departments panel to the content
            contentPanel.add(addDepartmentsPanel, "Add Departments");

            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Insert data into the database
                    addDepartmentToDatabase(deptNameField.getText(), numberOfStaffField.getText());
                    // Refresh the departments table
                    loadDepartmentsData(departmentTableModel);
                    cl.show(contentPanel, "Departments");
                }
            });

            cl.show(contentPanel, "Add Departments");
        });

        // Action listener for "Add Patient"
        addPatientButton.addActionListener(e -> {
            JPanel addPatientPanel = new JPanel();
            addPatientPanel.setBackground(new Color(245, 245, 245));

            JTextField patientNameField = new JTextField(15);
            JTextField patientAgeField = new JTextField(15);
            JTextField departmentHistoryField = new JTextField(15);

            JButton submitButton = new JButton("Add Patient");

            // Layout for adding a patient
            addPatientPanel.setLayout(new GridLayout(4, 2));
            addPatientPanel.add(new JLabel("Patient Name:"));
            addPatientPanel.add(patientNameField);
            addPatientPanel.add(new JLabel("Patient Age:"));
            addPatientPanel.add(patientAgeField);
            addPatientPanel.add(new JLabel("Department History ID:"));
            addPatientPanel.add(departmentHistoryField);
            addPatientPanel.add(submitButton);

            // Add the add patient panel to the content
            contentPanel.add(addPatientPanel, "Add Patient");

            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Insert data into the database
                    addPatientToDatabase(patientNameField.getText(), patientAgeField.getText(), departmentHistoryField.getText());
                    cl.show(contentPanel, "Review Patients");
                }
            });

            cl.show(contentPanel, "Add Patient");
        });

        // Action listener for "Review Patients"
        reviewPatientsButton.addActionListener(e -> {
            JPanel reviewPatientsPanel = new JPanel();
            reviewPatientsPanel.setBackground(new Color(245, 245, 245));

            // Fetch patient data from the database and display in a table
            String[] patientColumns = {"ID", "Name", "Age", "Department History ID"};
            JTable patientTable = new JTable();
            DefaultTableModel patientTableModel = new DefaultTableModel(new Object[][]{}, patientColumns);
            patientTable.setModel(patientTableModel);

            // Fetch data from database and populate the table
            loadPatientsData(patientTableModel);

            JScrollPane patientScrollPane = new JScrollPane(patientTable);
            reviewPatientsPanel.add(patientScrollPane);

            // Add the review patients panel to the content
            contentPanel.add(reviewPatientsPanel, "Review Patients");

            cl.show(contentPanel, "Review Patients");
        });

        // Show the frame
        frame.setVisible(true);
    }

    // Method to load appointment data from the database
    private static void loadAppointmentData(DefaultTableModel model) {
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";
        String query = "SELECT * FROM appointment";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Clear the existing data
            model.setRowCount(0);

            // Populate the table with new data
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("appointmentid"),
                    rs.getInt("patientid"),
                    rs.getDate("dateofappointment"),
                    rs.getInt("doctorid")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Method to load presciption data from the database
    private static void loadpresciptionsData(DefaultTableModel model) {
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";
        String query = "SELECT * FROM presciption";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            model.setRowCount(0);

            // Populate the table with new data
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("presciptionid"),
                    rs.getInt("patientid"),
                    rs.getString("medications")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Method to load department data from the database
    private static void loadDepartmentsData(DefaultTableModel model) {
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";
        String query = "SELECT deptid, deptname, numberofstaff FROM departments";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Clear the existing data
            model.setRowCount(0);

            // Populate the table with new data
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("deptid"),
                    rs.getString("deptname"),
                    rs.getInt("numberofstaff")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Method to insert an appointment into the database
    private static void addAppointmentToDatabase(String patientID, String doctorID, String dateOfAppointment) {
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";

        String query = "INSERT INTO appointment (patientid, doctorid, dateofappointment) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(patientID));
            pstmt.setInt(2, Integer.parseInt(doctorID));
            pstmt.setDate(3, Date.valueOf(dateOfAppointment));
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Method to insert a presciption into the database
    private static void addpresciptionToDatabase(String patientID, String medications) {
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";

        String query = "INSERT INTO presciption (patientid, medications) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(patientID));
            pstmt.setString(2, medications);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Method to insert a department into the database
    private static void addDepartmentToDatabase(String deptName, String numberOfStaff) {
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";

        String query = "INSERT INTO departments (deptname, numberofstaff) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, deptName);
            pstmt.setInt(2, Integer.parseInt(numberOfStaff));
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Method to insert a patient into the database
    private static void addPatientToDatabase(String name, String age, String departmentHistory) {
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";

        String query = "INSERT INTO patients (pat_name, pat_age, department_history) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, Integer.parseInt(age));
            pstmt.setInt(3, Integer.parseInt(departmentHistory));
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Method to load patient data from the database
    private static void loadPatientsData(DefaultTableModel model) {
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";
        String query = "SELECT pat_id, pat_name, pat_age, department_history FROM patients";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Clear the existing data
            model.setRowCount(0);

            // Populate the table with new data
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("pat_id"),
                    rs.getString("pat_name"),
                    rs.getInt("pat_age"),
                    rs.getInt("department_history")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}