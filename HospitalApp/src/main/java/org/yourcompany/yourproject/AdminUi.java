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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class AdminUi {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout
        frame.setLayout(new BorderLayout());
// Income Panel

JPanel incomePanel = new JPanel();
incomePanel.setBackground(new Color(245, 245, 245));

// Table to display income data
String[] incomeColumns = {"Income ID", "Amount", "Income Date", "Description", "Payment Method"};
JTable incomeTable = new JTable();
DefaultTableModel incomeTableModel = new DefaultTableModel(new Object[][]{}, incomeColumns);
incomeTable.setModel(incomeTableModel);
loadIncomeData(incomeTableModel);

String[] ExpenseColumns = {"Expense ID", "Amount", "Expense Date", "Description", "Payment Method"};
JTable ExpenseTable = new JTable();
DefaultTableModel ExpenseTableModel = new DefaultTableModel(new Object[][]{}, ExpenseColumns);
ExpenseTable.setModel(ExpenseTableModel);
loadExpensesData(ExpenseTableModel);

JScrollPane incomeScrollPane = new JScrollPane(incomeTable);
incomePanel.add(incomeScrollPane);
        JPanel addBillPanel = new JPanel();
addBillPanel.setBackground(new Color(245, 245, 245));
addBillPanel.setLayout(new GridLayout(5, 2, 10, 10));

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 102)); // Dark teal color
        JLabel headerLabel = new JLabel("Admin Dashboard", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        frame.add(headerPanel, BorderLayout.NORTH);
        String[] billColumns = {"Bill ID", "Patient ID", "Total Amount", "Payment Method", "Attending Doctor"};
JTable billTable = new JTable();
DefaultTableModel billTableModel = new DefaultTableModel(new Object[][]{}, billColumns);
billTable.setModel(billTableModel);
loadBillsData(billTableModel);
        JPanel billsPanel = new JPanel();
        billsPanel.setLayout(new BorderLayout());
        billsPanel.setBackground(new Color(245, 245, 245));
        // Menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(8, 1, 10, 10)); // Updated grid to include new buttons
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        menuPanel.setBackground(new Color(224, 255, 255)); // Light cyan color
        // Panel for viewing accountants
JPanel accountantsPanel = new JPanel();
accountantsPanel.setLayout(new BorderLayout());
accountantsPanel.setBackground(new Color(245, 245, 245));

// Table to display accountants data
String[] accountantColumns = {"ID", "Name", "Salary"};
JTable accountantTable = new JTable();
DefaultTableModel accountantTableModel = new DefaultTableModel(new Object[][]{}, accountantColumns);
accountantTable.setModel(accountantTableModel);

// Fetch accountants data from the database
loadAccountantsData(accountantTableModel);

JScrollPane accountantScrollPane = new JScrollPane(accountantTable);
accountantsPanel.add(accountantScrollPane, BorderLayout.CENTER);

JScrollPane billScrollPane = new JScrollPane(billTable);
billsPanel.add(billScrollPane, BorderLayout.CENTER);
// Panel for adding an accountant
JPanel addAccountantPanel = new JPanel();
addAccountantPanel.setBackground(new Color(245, 245, 245));
addAccountantPanel.setLayout(new GridLayout(3, 2, 10, 10));

JTextField accountantNameField = new JTextField(15);
JButton addAccountantButton = new JButton("Add Accountant");
JButton viewBillsButton = new JButton("View Bills"); // New button
addAccountantPanel.add(new JLabel("Accountant Name:"));
addAccountantPanel.add(accountantNameField);
addAccountantPanel.add(addAccountantButton);
JTextField patientIdField = new JTextField(15);
JTextField totalAmountField = new JTextField(15);
JTextField paymentMethodField = new JTextField(15);
JTextField attendingDoctorField = new JTextField(15);
JButton submitBillButton = new JButton("Add Bill");

        // Buttons for different functionalities
        JButton appointmentButton = new JButton("Appointment");
        JButton departmentsButton = new JButton("Departments");
        JButton presciptionButton = new JButton("Prescription");
        JButton addAppointmentButton = new JButton("Add Appointment");
        JButton addPrescriptionButton = new JButton("Add Prescription");
        JButton addDepartmentsButton = new JButton("Add Departments");
        JButton addPatientButton = new JButton("Add Patient");
        JButton reviewPatientsButton = new JButton("Review Patients");
        JButton viewAccountantsButton = new JButton("View Accountants"); // New button
        JButton AddUser = new JButton("Add User"); // New button
        JButton AddBill = new JButton("Add Bill"); // New button
        JButton addIncome = new JButton("Add Income");        
        JButton viewIncome = new JButton("View Income");
        JButton addExpenses = new JButton("Add Expenses");        
        JButton viewExpenses = new JButton("View Expenses");



        // Style buttons
        JButton[] buttons = {appointmentButton, departmentsButton, presciptionButton, 
            addAppointmentButton, addPrescriptionButton, 
            addDepartmentsButton, addPatientButton, reviewPatientsButton,
             viewAccountantsButton, AddUser, viewBillsButton,AddBill,
             viewIncome, addIncome, addExpenses, viewExpenses};
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
        menuPanel.add(addPrescriptionButton);
        menuPanel.add(addDepartmentsButton);
        menuPanel.add(addPatientButton);
        menuPanel.add(reviewPatientsButton);
        menuPanel.add(viewAccountantsButton); // Add to menu
        menuPanel.add(AddUser); // Add to menu
        menuPanel.add(viewBillsButton); // Add to menu
        menuPanel.add(AddBill); // Add to 
        menuPanel.add(viewIncome); // Add to menu
        menuPanel.add(addIncome); // Add to menu
        menuPanel.add(viewExpenses); // Add to menu
        menuPanel.add(addExpenses); // Add to menu

        frame.add(menuPanel, BorderLayout.WEST);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        contentPanel.setBackground(new Color(245, 245, 245)); // Light gray background
        contentPanel.add(incomePanel, "Income");
        addBillPanel.add(new JLabel("Patient ID:"));
addBillPanel.add(patientIdField);
addBillPanel.add(new JLabel("Total Amount:"));
addBillPanel.add(totalAmountField);
addBillPanel.add(new JLabel("Payment Method:"));
addBillPanel.add(paymentMethodField);
addBillPanel.add(new JLabel("Attending Doctor:"));
addBillPanel.add(attendingDoctorField);
addBillPanel.add(submitBillButton);
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

        // Fetch prescription from the database and display in a table
        String[] prescriptionColumns = {"ID", "Patient ID", "Medications"};
        JTable prescriptionTable = new JTable();
        DefaultTableModel prescriptionTableModel = new DefaultTableModel(new Object[][]{}, prescriptionColumns);
        prescriptionTable.setModel(prescriptionTableModel);

        // Fetch data from database and populate the table
        loadpresciptionsData(prescriptionTableModel);

        JScrollPane prescriptionScrollPane = new JScrollPane(prescriptionTable);
        presciptionsPanel.add(prescriptionScrollPane);

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
        contentPanel.add(presciptionsPanel, "prescription");
        contentPanel.add(accountantsPanel, "Accountants");
        contentPanel.add(billsPanel, "Bills");
        contentPanel.add(addBillPanel, "Add Bill");

        frame.add(contentPanel, BorderLayout.CENTER);

        submitBillButton.addActionListener(e -> {
            try {
                int patId = Integer.parseInt(patientIdField.getText());
                int totalAmount = Integer.parseInt(totalAmountField.getText());
                int paymentMethod = Integer.parseInt(paymentMethodField.getText());
                int attendingDoctor = Integer.parseInt(attendingDoctorField.getText());
        
                // Call the method to add the bill to the database
                addBillsToDatabase(patId, totalAmount, paymentMethod, attendingDoctor);
        
                // Optionally, show a success message
                JOptionPane.showMessageDialog(frame, "Bill added successfully!");
        
                // Clear the fields after submission
                patientIdField.setText("");
                totalAmountField.setText("");
                paymentMethodField.setText("");
                attendingDoctorField.setText("");
        
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        viewExpenses.addActionListener(e -> {
            loadExpensesData(ExpenseTableModel); // Refresh the expenses table
            CardLayout cl = (CardLayout) (contentPanel.getLayout());
            cl.show(contentPanel, "Expenses"); // Show the expenses panel
        });
        
        CardLayout cl = (CardLayout) (contentPanel.getLayout());
        viewIncome.addActionListener(e -> {
            loadIncomeData(incomeTableModel); // Refresh the income table
            cl.show(contentPanel, "Income"); // Show the income panel
        });
        AddUser.addActionListener(e -> AddNewUser.main(args));
        appointmentButton.addActionListener(e -> cl.show(contentPanel, "appointment"));
        AddBill.addActionListener(e -> {
            // Show the add bill panel
            cl.show(contentPanel, "Add Bill");
        });
        departmentsButton.addActionListener(e -> cl.show(contentPanel, "Departments"));
        presciptionButton.addActionListener(e -> cl.show(contentPanel, "prescription"));
        reviewPatientsButton.addActionListener(e -> {
            JPanel reviewPatientsPanel = new JPanel();
            reviewPatientsPanel.setBackground(new Color(245, 245, 245));           
        
            // Create a table to display patient data
            String[] patientColumns = {"ID", "Name", "Age", "Department History"};
            JTable patientTable = new JTable();
            DefaultTableModel patientTableModel = new DefaultTableModel(new Object[][]{}, patientColumns);
            patientTable.setModel(patientTableModel);
        
            // Load patient data from the database
            loadPatientsData(patientTableModel);
        
            JScrollPane patientScrollPane = new JScrollPane(patientTable);
            reviewPatientsPanel.add(patientScrollPane);
        
            // Add the review patients panel to the content
            contentPanel.add(reviewPatientsPanel, "Review Patients");
        
            // Show the review patients panel
            cl.show(contentPanel, "Review Patients");
        });
        JPanel expensesPanel = new JPanel();
expensesPanel.setBackground(new Color(245, 245, 245));

// Table to display expenses data
String[] expenseColumns = {"Expense ID", "Amount", "Expense Date", "Description", "Payment Method"};
JTable expenseTable = new JTable();
DefaultTableModel expenseTableModel = new DefaultTableModel(new Object[][]{}, expenseColumns);
expenseTable.setModel(expenseTableModel);
loadExpensesData(expenseTableModel);

JScrollPane expenseScrollPane = new JScrollPane(expenseTable);
expensesPanel.add(expenseScrollPane);

// Add the expenses panel to the content panel
contentPanel.add(expensesPanel, "Expenses");
             addIncome.addActionListener(e -> {
            System.out.println("Add Income button clicked");
            showAddIncomeDialog();
        });
        addExpenses.addActionListener(e -> {
            System.out.println("Add Expenses button clicked");
            showAddExpenseDialog();
        });
        viewBillsButton.addActionListener(e -> {
            loadBillsData(billTableModel); // Refresh the bills table
            cl.show(contentPanel, "Bills"); // Show the bills panel
        });
        addExpenses.addActionListener(e -> {
            System.out.println("Add Expenses button clicked");
            showAddExpenseDialog();
        });
        viewAccountantsButton.addActionListener(e -> {
            // Show the accountants panel
            loadAccountantsData(accountantTableModel); // Refresh the accountants table
            cl.show(contentPanel, "Accountants");
        });
        // Action listener for "Add Department"
addDepartmentsButton.addActionListener(e -> {
    JPanel addDepartmentPanel = new JPanel();
    addDepartmentPanel.setBackground(new Color(245, 245, 245));

    JTextField deptNameField = new JTextField(15);
    JTextField staffCountField = new JTextField(15);

    JButton submitButton = new JButton("Add Department");

    // Layout for adding a department
    addDepartmentPanel.setLayout(new GridLayout(3, 2));
    addDepartmentPanel.add(new JLabel("Department Name:"));
    addDepartmentPanel.add(deptNameField);
    addDepartmentPanel.add(new JLabel("Staff Count:"));
    addDepartmentPanel.add(staffCountField);
    addDepartmentPanel.add(submitButton);

    // Add the add department panel to the content
    contentPanel.add(addDepartmentPanel, "Add Department");
    submitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Insert department into the database
            String deptName = deptNameField.getText();
            int staffCount = Integer.parseInt(staffCountField.getText());
            addDepartmentToDatabase(deptName, staffCount);

            // Refresh the departments table (optional)
            loadDepartmentsData(departmentTableModel);

            // Go back to the departments view
            cl.show(contentPanel, "Departments");
        }
    });

    cl.show(contentPanel, "Add Department");
});
addPatientButton.addActionListener(e -> {
    JPanel addPatientPanel = new JPanel();
    addPatientPanel.setBackground(new Color(245, 245, 245));

    JTextField patientNameField = new JTextField(15);
    JTextField patientAgeField = new JTextField(15);
    JTextField patientGenderField = new JTextField(15);

    JButton submitButton = new JButton("Add Patient");

    // Layout for adding a patient
    addPatientPanel.setLayout(new GridLayout(4, 2));
    addPatientPanel.add(new JLabel("Patient Name:"));
    addPatientPanel.add(patientNameField);
    addPatientPanel.add(new JLabel("Patient Age:"));
    addPatientPanel.add(patientAgeField);
    addPatientPanel.add(new JLabel("Department history:"));
    addPatientPanel.add(patientGenderField);
    addPatientPanel.add(submitButton);
    contentPanel.add(addPatientPanel, "Add Patient");

    submitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Insert patient into the database
            String patientName = patientNameField.getText();
            String patientAge =patientAgeField.getText();
            String deptHistory = patientGenderField.getText();
            addPatientToDatabase(patientName, patientAge, deptHistory);
            cl.show(contentPanel, "Review Patients");
        }
    });

    cl.show(contentPanel, "Add Patient");
});
addAccountantButton.addActionListener(e -> {
    String accountantName = accountantNameField.getText().trim();

    if (accountantName.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "Accountant name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    loadAccountantsData(accountantTableModel); // Refresh the accountants table
    JOptionPane.showMessageDialog(frame, "Accountant added successfully!");
});
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
        

        // Action listener for "Add Prescription"
        addPrescriptionButton.addActionListener(e -> {
            JPanel addPrescriptionPanel = new JPanel();
            addPrescriptionPanel.setBackground(new Color(245, 245, 245));

            JTextField patientIDField = new JTextField(15);
            JTextField medicationsField = new JTextField(15);

            JButton submitButton = new JButton("Add Prescription");

            // Layout for adding a prescription
            addPrescriptionPanel.setLayout(new GridLayout(3, 2));
            addPrescriptionPanel.add(new JLabel("Patient ID:"));
            addPrescriptionPanel.add(patientIDField);
            addPrescriptionPanel.add(new JLabel("Medications:"));
            addPrescriptionPanel.add(medicationsField);
            addPrescriptionPanel.add(submitButton);

            // Add the add prescription panel to the content
            contentPanel.add(addPrescriptionPanel, "Add Prescription");

            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Insert data into the database
                    addpresciptionToDatabase(patientIDField.getText(), medicationsField.getText());
                    // Refresh the prescription table
                    loadpresciptionsData(prescriptionTableModel);
                    cl.show(contentPanel, "prescription");
                }
            });

            cl.show(contentPanel, "Add Prescription");
        });

        frame.setVisible(true);
    }
    
    

    private static void showAddIncomeDialog() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
    
        JTextField amountField = new JTextField();
        JTextField dateField = new JTextField(); // Enter date in 'yyyy-mm-dd' format
        JTextField descriptionField = new JTextField();
    
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel("Income Date (YYYY-MM-DD):"));
        panel.add(dateField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
    
        int option = JOptionPane.showConfirmDialog(null, panel, "Add Income", JOptionPane.OK_CANCEL_OPTION);
    
        if (option == JOptionPane.OK_OPTION) {
            double amount = Double.parseDouble(amountField.getText());
            String date = dateField.getText();
            String description = descriptionField.getText();
    
            String url = "jdbc:postgresql://localhost:5432/hospital";
            String user = "hospitaladmin";
            String password = "hospitaladmin";
            String query = "INSERT INTO Income(AMOUNT, INCOMEDATE, DESCRIPTION, PAYMENTMETHOD) VALUES (?, ?, ?, 1)";
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setDouble(1, amount);
                stmt.setDate(2, Date.valueOf(date));
                stmt.setString(3, description);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Income added successfully!");    
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding income: " + ex.getMessage());
            }
        }
    }
    // Placeholder for database methods
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
    private static void loadBillsData(DefaultTableModel model) {
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";
        String query = "SELECT * FROM BILLS";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Clear the existing data
            model.setRowCount(0);

            // Populate the table with new data
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("BILLID"),
                    rs.getString("PATIENTID"),
                    rs.getInt("TOTALAMOUNT"),
                    rs.getInt("PAYMENTMETHOD"),
                    rs.getInt("ATTENDINGDOCTOR")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private static void addBillsToDatabase(int pat_id, int totalAmount,
     int PaymentMethod, int ATTENDINGDOCTOR) {
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";
        String query = """
                       INSERT INTO BILLS(PATIENTID, TOTALAMOUNT,
                        PAYMENTMETHOD, ATTENDINGDOCTOR)
                       VALUES(?, ?, ?, ?)
                       """;
                        try (Connection conn = DriverManager.getConnection(url, user, password);
                        PreparedStatement pstmt = conn.prepareStatement(query)) {
                       pstmt.setInt(1, pat_id);
                       pstmt.setInt(2, totalAmount);
                       pstmt.setInt(3, PaymentMethod);
                       pstmt.setInt(4, ATTENDINGDOCTOR);
                       pstmt.executeUpdate();
                   } catch (SQLException ex) {
                       ex.printStackTrace();
                   }
    }
    private static void showAddExpenseDialog() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
    
        JTextField amountField = new JTextField();
        JTextField dateField = new JTextField(); // Enter date in 'yyyy-mm-dd' format
        JTextField descriptionField = new JTextField();    
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel("Expense Date (YYYY-MM-DD):"));
        panel.add(dateField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);    
        int option = JOptionPane.showConfirmDialog(null, panel, "Add Expense", JOptionPane.OK_CANCEL_OPTION);
    
        if (option == JOptionPane.OK_OPTION) {
            double amount = Double.parseDouble(amountField.getText());
            String date = dateField.getText();
            String description = descriptionField.getText();
    
            String url = "jdbc:postgresql://localhost:5432/hospital";
            String user = "hospitaladmin";
            String password = "hospitaladmin";
            String query = "INSERT INTO EXPENSES(AMOUNT, EXPENSEDATE, DESCRIPTION, PAYMENTMETHOD) VALUES (?, ?, ?, 1)";
    
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setDouble(1, amount);
                stmt.setDate(2, Date.valueOf(date));
                stmt.setString(3, description);
                stmt.executeUpdate();
    
                JOptionPane.showMessageDialog(null, "Expense added successfully!");    
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding expense: " + ex.getMessage());
            }
        }
    }
    private static void loadExpensesData(DefaultTableModel model) {
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";
        String query = "SELECT EXPENSEID, amount, expensedate, description, paymentmethod FROM expenses"; // Adjust the query based on your database schema
    
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            // Clear the existing data
            model.setRowCount(0);
    
            // Populate the table with new data
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("expenseid"),
                    rs.getDouble("amount"), // Assuming amount is a double
                    rs.getDate("expensedate"), // Assuming incomedate is a Date
                    rs.getString("description"),
                    rs.getString("paymentmethod")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private static void loadIncomeData(DefaultTableModel model) {
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";
        String query = "SELECT incomeid, amount, incomedate, description, paymentmethod FROM income"; // Adjust the query based on your database schema
    
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            // Clear the existing data
            model.setRowCount(0);
    
            // Populate the table with new data
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("incomeid"),
                    rs.getDouble("amount"), // Assuming amount is a double
                    rs.getDate("incomedate"), // Assuming incomedate is a Date
                    rs.getString("description"),
                    rs.getString("paymentmethod")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private static void loadDoctorsData(DefaultTableModel model) {
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";
        String query = """
                       SELECT DOC.DOCID, US.USERNAME, DEPARTMENT,
                       SALARY FROM DOCTORS DOC
                       JOIN HOSPITALUSERS US ON US.USERID = DOC.DOCID""" ;    
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("docid"),
                    rs.getString("username"),
                    rs.getString("department"),
                    rs.getString("salary")
                });
            }
            System.out.println("data retived");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private static void loadNursesData(DefaultTableModel model) {
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";
        String query = """
                       SELECT NU.NURSEID, US.USERNAME, 
                        NU.DEPTID, NU.SALARY
                        FROM NURSES NU
                        JOIN HOSPITALUSERS US ON US.USERID = NU.NURSEID;""" ;    
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("NURSEID"),
                    rs.getString("username"),
                    rs.getString("deptid"),
                    rs.getString("salary")
                });
            }
            System.out.println("data retived");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private static void loadAccountantsData(DefaultTableModel model) {
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";
        String query = "SELECT acc.accid, us.username, acc.salary FROM accountants acc join hospitalusers us on us.userid = acc.accid"; // Adjust the query based on your database schema
    
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
    
            // Clear the existing data
            model.setRowCount(0);
    
            // Populate the table with new data
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("accid"),
                    rs.getString("username"),
                    rs.getString("salary")
                });
            }
            System.out.println("data retived");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private static void addDepartmentToDatabase(String deptName, int staffCount) {
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";
    
        String query = "INSERT INTO departments (deptname, numberofstaff) VALUES (?, ?)";
    
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, deptName);
            pstmt.setInt(2, staffCount);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    

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
    
}
