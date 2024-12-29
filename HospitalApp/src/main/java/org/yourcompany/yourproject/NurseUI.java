package org.yourcompany.yourproject;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class NurseUI {

    // Database connection URL
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/hospital";
    private static final String USER = "hospitaladmin";
    private static final String PASSWORD = "hospitaladmin";

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Nurse Dashboard");
        frame.setSize(1000, 600);  // Increased frame size to accommodate the table
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout
        frame.setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 102)); // Dark teal color
        JLabel headerLabel = new JLabel("Nurse Dashboard", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(5, 1, 10, 10)); // Adjusted to show 5 buttons
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        menuPanel.setBackground(new Color(224, 255, 255)); // Light cyan color

        // Buttons for the new functionalities
        JButton appointmentsButton = new JButton("Appointments");
        JButton prescriptionButton = new JButton("Prescription");
        JButton roomsButton = new JButton("Rooms");
        JButton assignRoomButton = new JButton("Assign Room");
        JButton addRoomButton = new JButton("Add Room");
        JButton deAssignRoomButton = new JButton("deAssign Room");


        // Style buttons
        JButton[] buttons = {appointmentsButton, prescriptionButton,
            roomsButton, assignRoomButton, addRoomButton, deAssignRoomButton};
        for (JButton button : buttons) {
            button.setBackground(new Color(0, 153, 153)); // Teal color
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 14));
        }

        // Add buttons to menu panel
        menuPanel.add(appointmentsButton);
        menuPanel.add(prescriptionButton);
        menuPanel.add(roomsButton);
        menuPanel.add(assignRoomButton);
        menuPanel.add(addRoomButton);
        menuPanel.add(deAssignRoomButton);


        frame.add(menuPanel, BorderLayout.WEST);

        // Content panel with CardLayout for different views
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        contentPanel.setBackground(new Color(245, 245, 245)); // Light gray background

        // Placeholder panels for each functionality
        JPanel appointmentsPanel = new JPanel();
        appointmentsPanel.setBackground(new Color(245, 245, 245));
        appointmentsPanel.add(new JLabel("Appointments"));

        JPanel prescriptionPanel = new JPanel();
        prescriptionPanel.setBackground(new Color(245, 245, 245));
        prescriptionPanel.add(new JLabel("Prescription"));

        // Panel for Rooms with JTable to show room data
        JPanel roomsPanel = new JPanel();
        roomsPanel.setBackground(new Color(245, 245, 245));
        roomsPanel.setLayout(new BorderLayout());

        // Create the table for displaying room data
        JTable roomsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(roomsTable);
        roomsPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel for Add Room functionality
        JPanel addRoomPanel = new JPanel();
        addRoomPanel.setBackground(new Color(245, 245, 245));
        addRoomPanel.setLayout(new GridLayout(4, 2, 10, 10));
        

        // Labels and text fields for room details
        JLabel isOccupiedLabel = new JLabel("Is Occupied (Yes/No):");
        JLabel dailyRateLabel = new JLabel("Daily Rate:");

        JTextField isOccupiedField = new 
        JTextField(10);
        JTextField dailyRateField = new JTextField(10);

        // Button to add room
        JButton addRoomButtonAction = new JButton("Add Room");

        // Add components to addRoomPanel
        addRoomPanel.add(isOccupiedLabel);
        addRoomPanel.add(isOccupiedField);
        addRoomPanel.add(dailyRateLabel);
        addRoomPanel.add(dailyRateField);
        addRoomPanel.add(addRoomButtonAction);

        contentPanel.add(appointmentsPanel, "Appointments");
        contentPanel.add(prescriptionPanel, "Prescription");
        contentPanel.add(roomsPanel, "Rooms");
        contentPanel.add(addRoomPanel, "AddRoom");

        // Panel for Assign Room functionality
        JPanel assignRoomPanel = new JPanel();
        assignRoomPanel.setBackground(new Color(245, 245, 245));
        assignRoomPanel.setLayout(new GridLayout(3, 2, 10, 10));

        // Labels and components for assigning a room
        JLabel roomLabel = new JLabel("Select Room:");
        JComboBox<String> roomComboBox = new JComboBox<>();
        JLabel patientIdLabel = new JLabel("Patient ID:");
        JTextField patientIdField = new JTextField(10);
        JButton assignRoomButtonAction = new JButton("Assign Room");

        // Add components to assignRoomPanel
        assignRoomPanel.add(roomLabel);
        assignRoomPanel.add(roomComboBox);
        assignRoomPanel.add(patientIdLabel);
        assignRoomPanel.add(patientIdField);
        assignRoomPanel.add(assignRoomButtonAction);

        // Add the assignRoomPanel to the contentPanel
        contentPanel.add(assignRoomPanel, "AssignRoom");

        frame.add(contentPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        CardLayout cl = (CardLayout) (contentPanel.getLayout());

        appointmentsButton.addActionListener(e -> cl.show(contentPanel, "Appointments"));
        prescriptionButton.addActionListener(e -> cl.show(contentPanel, "Prescription"));
        roomsButton.addActionListener(e -> {
            // Fetch rooms data from the database when "Rooms" button is clicked
            fetchRoomsData(roomsTable);
            cl.show(contentPanel, "Rooms");
        });
        assignRoomButton.addActionListener(e -> {
            fetchAvailableRooms(roomComboBox);
            cl.show(contentPanel, "AssignRoom");
        });
        addRoomButton.addActionListener(e -> cl.show(contentPanel, "AddRoom"));

        // Action listener for the "Add Room" button
        addRoomButtonAction.addActionListener(e -> {
            String isOccupied = isOccupiedField.getText();
            String dailyRate = dailyRateField.getText();

            if (addRoom(isOccupied, dailyRate)) {
                JOptionPane.showMessageDialog(frame, "Room added successfully!");
                fetchRoomsData(roomsTable);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to add room.");
            }
        });
// Action listener for the "De-Assign Room" button
// Panel for De-Assign Room functionality
JPanel deAssignRoomPanel = new JPanel();
deAssignRoomPanel.setBackground(new Color(245, 245, 245));
deAssignRoomPanel.setLayout(new GridLayout(2, 2, 10, 10));

// Labels and components for de-assigning a room
JLabel deAssignRoomLabel = new JLabel("Select Room to De-Assign:");
JComboBox<String> deAssignRoomComboBox = new JComboBox<>();
JButton deAssignRoomButtonAction = new JButton("De-Assign Room");

// Add components to deAssignRoomPanel
deAssignRoomPanel.add(deAssignRoomLabel);
deAssignRoomPanel.add(deAssignRoomComboBox);
deAssignRoomPanel.add(deAssignRoomButtonAction);

// Add the deAssignRoomPanel to the contentPanel
contentPanel.add(deAssignRoomPanel, "DeAssignRoom");

// Action listener for the "De-Assign Room" button
deAssignRoomButtonAction.addActionListener(e -> {
    String selectedRoom = (String) deAssignRoomComboBox.getSelectedItem(); // Get selected room from the combo box

    if (selectedRoom != null && !selectedRoom.isEmpty()) {
        if (deAssignRoom(selectedRoom)) {
            JOptionPane.showMessageDialog(frame, "Room de-assigned successfully!");
            fetchRoomsData(roomsTable); // Refresh the rooms table to show updated room data
            fetchAvailableRooms(deAssignRoomComboBox); // Refresh the available rooms in the combo box
        } else {
            JOptionPane.showMessageDialog(frame, "Failed to de-assign room.");
        }
    } else {
        JOptionPane.showMessageDialog(frame, "Please select a room to de-assign.");
    }
});

// When the "De-Assign Room" button is clicked, populate the deAssignRoomComboBox
deAssignRoomButton.addActionListener(e -> {
    fetchUnavailableRooms(deAssignRoomComboBox); // Populate the combo box with available rooms
    cl.show(contentPanel, "DeAssignRoom"); // Show the de-assign room panel
});
        // Action listener for the "Assign Room" button
        assignRoomButtonAction.addActionListener(e -> {
            String selectedRoom = (String) roomComboBox.getSelectedItem();
            String patientId = patientIdField.getText();

            if (assignRoomToPatient(selectedRoom, patientId)) {
                JOptionPane.showMessageDialog(frame, "Room assigned successfully!");
                fetchRoomsData(roomsTable); // Refresh the rooms table
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to assign room.");
            }
        });

        // Fetch data for appointments and prescriptions
        fetchAppointmentsData(appointmentsPanel);
        fetchPrescriptionsData(prescriptionPanel);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to fetch room data from PostgreSQL database and display it in JTable
    private static void fetchRoomsData(JTable roomsTable) {
        String query = "SELECT roomid, isoccupied, dailyrate FROM rooms";

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Room ID");
        model.addColumn("Occupied?");
        model.addColumn("Daily Rate");

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = new Object[3];
                row[0] = rs.getInt("roomid");
                row[1] = rs.getString("isoccupied");
                row[2] = rs.getBigDecimal("dailyrate");
                model.addRow(row);
            }

            roomsTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching data from database: " + e.getMessage());
        }
    }

    // Method to fetch available rooms from PostgreSQL database and populate JComboBox
    private static void fetchAvailableRooms(JComboBox<String> roomComboBox) {
        String query = "SELECT roomid FROM rooms WHERE isoccupied = 'No'";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            roomComboBox.removeAllItems();

            while (rs.next()) {
                roomComboBox.addItem(String.valueOf(rs.getInt("roomid")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching available rooms: " + e.getMessage());
        }
    }

    private static void fetchUnavailableRooms(JComboBox<String> roomComboBox) {
        String query = "SELECT roomid FROM rooms WHERE isoccupied = 'Yes'";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            roomComboBox.removeAllItems();

            while (rs.next()) {
                roomComboBox.addItem(String.valueOf(rs.getInt("roomid")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching available rooms: " + e.getMessage());
        }
    }
    // Method to fetch prescription data from PostgreSQL database and display it in JTable
    private static void fetchPrescriptionsData(JPanel prescriptionPanel) {
        String query = "SELECT presciptionid, patientid, medications FROM presciption";

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Prescription ID");
        model.addColumn("Patient ID");
        model.addColumn("Medications");

        JTable prescriptionTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(prescriptionTable);
        prescriptionPanel.setLayout(new BorderLayout());
        prescriptionPanel.add(scrollPane, BorderLayout.CENTER);

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = new Object[3];
                row[0] = rs.getInt("presciptionid");
                row[1] = rs.getInt("patientid");
                row[2] = rs.getString("medications");
                model.addRow(row);
            }

            prescriptionTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching data from database: " + e.getMessage());
        }
    }

    // Method to fetch appointment data from PostgreSQL database and display it in JTable
    private static void fetchAppointmentsData(JPanel appointmentsPanel) {
        String query = "SELECT appointmentid, dateofappointment, patientid, doctorid FROM appointment";

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Appointment ID");
        model.addColumn("Date of Appointment");
        model.addColumn("Patient ID");
        model.addColumn("Doctor ID");

        JTable appointmentsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        appointmentsPanel.setLayout(new BorderLayout());
        appointmentsPanel.add(scrollPane, BorderLayout.CENTER);

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = new Object[4];
                row[0] = rs.getInt("appointmentid");
                row[1] = rs.getString("dateofappointment");
                row[2] = rs.getInt("patientid");
                row[3] = rs.getInt("doctorid");
                model.addRow(row);
            }

            appointmentsTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching data from database: " + e.getMessage());
        }
    }

    // Method to add room data to PostgreSQL database
    private static boolean addRoom(String isOccupied, String dailyRate) {
        String query = "INSERT INTO rooms (isoccupied, dailyrate) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, isOccupied);
            stmt.setBigDecimal(2, new java.math.BigDecimal(dailyRate));

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to assign a room to a patient in the PostgreSQL database
    private static boolean assignRoomToPatient(String roomId, String patientId) {
        String query = "UPDATE rooms SET patientid = ?, isoccupied = 'Yes' WHERE roomid = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(patientId));
            stmt.setInt(2, Integer.parseInt(roomId));
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e){
        e.printStackTrace();
        return false;
    }
}
    // Method to assign a room to a patient in the PostgreSQL database
   // Method to de-assign a room from a patient in the PostgreSQL database
   private static boolean deAssignRoom(String roomId) {
    String query = "UPDATE rooms SET patientid = NULL, isoccupied = 'No' WHERE roomid = ?";

    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
         PreparedStatement stmt = connection.prepareStatement(query)) {

        // Convert roomId to an integer
        stmt.setInt(1, Integer.parseInt(roomId)); // Ensure roomId is an integer
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    } catch (NumberFormatException e) {
        e.printStackTrace();
        return false; // Handle the case where roomId is not a valid integer
    }
}
}