package org.yourcompany.yourproject;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class AccountantGUI {
    private static JTable billTable;
    private static JTable incomeTable;
    private static JTable expenseTable;

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Accountant Dashboard");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout
        frame.setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 102)); // Dark teal color
        JLabel headerLabel = new JLabel("Accountant Dashboard", JLabel.CENTER);
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
        JButton viewBillsButton = new JButton("View Bills");
        JButton addBillsButton = new JButton("Add Bills");
        JButton viewIncomeButton = new JButton("View Income");
        JButton addIncomeButton = new JButton("Add Income");
        JButton viewExpensesButton = new JButton("View Expenses");
        JButton addExpensesButton = new JButton("Add Expenses");

        JButton[] buttons = {viewBillsButton, addBillsButton, 
            viewIncomeButton, addIncomeButton, 
            viewExpensesButton, addExpensesButton};
        for (JButton button : buttons) {
            button.setBackground(new Color(0, 153, 153)); // Teal color
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 14));
        }
        menuPanel.add(viewBillsButton);
        menuPanel.add(addBillsButton);
        menuPanel.add(viewIncomeButton);
        menuPanel.add(addIncomeButton);
        menuPanel.add(viewExpensesButton);
        menuPanel.add(addExpensesButton);

        frame.add(menuPanel, BorderLayout.WEST);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        contentPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        // Add the content panels for bills, income, and expenses
        contentPanel.add(createBillsPanel(), "Bills");
        contentPanel.add(createIncomePanel(), "Income");
        contentPanel.add(createExpensesPanel(), "Expenses");

        frame.add(contentPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        viewBillsButton.addActionListener(e -> {
            System.out.println("View Bills button clicked");
            loadBillsData();
            CardLayout cl = (CardLayout) (contentPanel.getLayout());
            cl.show(contentPanel, "Bills");
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        addBillsButton.addActionListener(e -> {
            System.out.println("Add Bills button clicked");
            showAddBillDialog();
        });
        viewIncomeButton.addActionListener(e -> {
            System.out.println("View Income button clicked");
            loadIncomeData();
            CardLayout cl = (CardLayout) (contentPanel.getLayout());
            cl.show(contentPanel, "Income");
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        addIncomeButton.addActionListener(e -> {
            System.out.println("Add Income button clicked");
            showAddIncomeDialog();
        });

        viewExpensesButton.addActionListener(e -> {
            System.out.println("View Expenses button clicked");
            loadExpensesData();
            CardLayout cl = (CardLayout) (contentPanel.getLayout());
            cl.show(contentPanel, "Expenses");
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        addExpensesButton.addActionListener(e -> {
            System.out.println("Add Expenses button clicked");
            showAddExpenseDialog();
        });

        frame.setVisible(true);
    }

    private static JPanel createBillsPanel() {
        JPanel billsPanel = new JPanel(new BorderLayout());
        String[] billColumns = {"Bill ID", "Patient ID", "Total Amount", "Payment Method", "Attending Doctor"};
        billTable = new JTable(new DefaultTableModel(new Object[][]{}, billColumns));
        JScrollPane billScrollPane = new JScrollPane(billTable);
        billsPanel.add(billScrollPane, BorderLayout.CENTER);
        return billsPanel;
    }

    private static JPanel createIncomePanel() {
        JPanel incomePanel = new JPanel(new BorderLayout());
        String[] incomeColumns = {"Income ID", "Amount", "Income Date", "Description", "Payment Method"};
        incomeTable = new JTable(new DefaultTableModel(new Object[][]{}, incomeColumns));
        JScrollPane incomeScrollPane = new JScrollPane(incomeTable);
        incomePanel.add(incomeScrollPane, BorderLayout.CENTER);
        return incomePanel;
    }

    private static JPanel createExpensesPanel() {
        JPanel expensesPanel = new JPanel(new BorderLayout());
        String[] expenseColumns = {"Expense ID", "Amount", "Expense Date", "Description", "Payment Method"};
        expenseTable = new JTable(new DefaultTableModel(new Object[][]{}, expenseColumns));
        JScrollPane expenseScrollPane = new JScrollPane(expenseTable);
        expensesPanel.add(expenseScrollPane, BorderLayout.CENTER);
        return expensesPanel;
    }

    private static void loadBillsData() {
        System.out.println("Loading Bills data...");
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";
        String query = "SELECT billid, patientid, totalamount, paymentmethod, attendingdoctor FROM public.bills";

        DefaultTableModel billTableModel = (DefaultTableModel) billTable.getModel();
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            billTableModel.setRowCount(0); // Clear existing data

            while (rs.next()) {
                billTableModel.addRow(new Object[]{
                    rs.getInt("billid"),
                    rs.getInt("patientid"),
                    rs.getDouble("totalamount"),
                    rs.getInt("paymentmethod"),
                    rs.getInt("attendingdoctor")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void loadIncomeData() {
        System.out.println("Loading Income data...");
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";
        String query = "SELECT incomeid, amount, incomedate, description, paymentmethod FROM public.income";

        DefaultTableModel incomeTableModel = (DefaultTableModel) incomeTable.getModel();
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            incomeTableModel.setRowCount(0); // Clear existing data

            while (rs.next()) {
                incomeTableModel.addRow(new Object[]{
                    rs.getInt("incomeid"),
                    rs.getDouble("amount"),
                    rs.getDate("incomedate"),
                    rs.getString("description"),
                    rs.getInt("paymentmethod")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void loadExpensesData() {
        System.out.println("Loading Expenses data...");
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";
        String query = "SELECT expenseid, amount, expensedate, description, paymentmethod FROM public.expenses";

        DefaultTableModel expenseTableModel = (DefaultTableModel) expenseTable.getModel();
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            expenseTableModel.setRowCount(0); // Clear existing data

            while (rs.next()) {
                expenseTableModel.addRow(new Object[]{
                    rs.getInt("expenseid"),
                    rs.getDouble("amount"),
                    rs.getDate("expensedate"),
                    rs.getString("description"),
                    rs.getInt("paymentmethod")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Dummy dialog methods
    private static void showAddBillDialog() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
    
        JTextField patientIdField = new JTextField();
        JTextField totalAmountField = new JTextField();
        JTextField doctorIdField = new JTextField();
    
        panel.add(new JLabel("Patient ID:"));
        panel.add(patientIdField);
        panel.add(new JLabel("Total Amount:"));
        panel.add(totalAmountField);
        panel.add(new JLabel("Attending Doctor ID:"));
        panel.add(doctorIdField);
    
        int option = JOptionPane.showConfirmDialog(null, panel, "Add Bill", JOptionPane.OK_CANCEL_OPTION);
    
        if (option == JOptionPane.OK_OPTION) {
            int patientId = Integer.parseInt(patientIdField.getText());
            double totalAmount = Double.parseDouble(totalAmountField.getText());
            int doctorId = Integer.parseInt(doctorIdField.getText());
    
            String url = "jdbc:postgresql://localhost:5432/hospital";
            String user = "hospitaladmin";
            String password = "hospitaladmin";
            String query = "INSERT INTO Bills(patientid, totalamount, paymentmethod, attendingdoctor) VALUES (?, ?, 1, ?)";
    
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setInt(1, patientId);
                stmt.setDouble(2, totalAmount);
                stmt.setInt(3, doctorId);
                stmt.executeUpdate();
    
                JOptionPane.showMessageDialog(null, "Bill added successfully!");
                loadBillsData(); // Refresh the data in the table
    
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding bill: " + ex.getMessage());
            }
        }
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
                loadIncomeData(); // Refresh the data in the table
    
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding income: " + ex.getMessage());
            }
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
                loadExpensesData(); // Refresh the data in the table
    
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding expense: " + ex.getMessage());
            }
        }
    }
}
