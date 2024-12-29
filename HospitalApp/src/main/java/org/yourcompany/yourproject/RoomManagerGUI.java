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
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class RoomManagerGUI {

    private JTable table;
    private DefaultTableModel tableModel;

    public RoomManagerGUI() {
        // Create main frame
        JFrame frame = new JFrame("Room Manager Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 102));
        JLabel headerLabel = new JLabel("Room Manager Dashboard", SwingConstants.CENTER);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Left Panel (Buttons)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(3, 1, 10, 10));
        leftPanel.setBackground(new Color(204, 255, 255));

        JButton btnAddRoom = new JButton("Add Room");
        JButton btnRemoveRoom = new JButton("Remove Room");
        JButton btnUpdateRoom = new JButton("Update Room");

        leftPanel.add(btnAddRoom);
        leftPanel.add(btnRemoveRoom);
        leftPanel.add(btnUpdateRoom);

        frame.add(leftPanel, BorderLayout.WEST);

        // Right Panel (Table)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Room ID", "Room Name"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(rightPanel, BorderLayout.CENTER);

        // Database connection setup
        String url = "jdbc:postgresql://localhost:5432/hospital";
        String user = "hospitaladmin";
        String password = "hospitaladmin";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            // Load initial data from database
            loadRoomData(connection);
            
            // Add Room Button Action
            btnAddRoom.addActionListener(e -> {
                String roomName = JOptionPane.showInputDialog(frame, "Enter Room Name:");
                if (roomName != null && !roomName.trim().isEmpty()) {
                    try {
                        PreparedStatement stmt = connection.prepareStatement("INSERT INTO rooms (name) VALUES (?)");
                        stmt.setString(1, roomName);
                        stmt.executeUpdate();
                        loadRoomData(connection);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(frame, "Error adding room: " + ex.getMessage());
                    }
                }
            });

            // Remove Room Button Action
            btnRemoveRoom.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int roomId = (int) tableModel.getValueAt(selectedRow, 0);
                    try {
                        PreparedStatement stmt = connection.prepareStatement("DELETE FROM rooms WHERE id = ?");
                        stmt.setInt(1, roomId);
                        stmt.executeUpdate();
                        loadRoomData(connection);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(frame, "Error removing room: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a room to remove.");
                }
            });

            // Update Room Button Action
            btnUpdateRoom.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int roomId = (int) tableModel.getValueAt(selectedRow, 0);
                    String newRoomName = JOptionPane.showInputDialog(frame, "Enter New Room Name:");
                    if (newRoomName != null && !newRoomName.trim().isEmpty()) {
                        try {
                            PreparedStatement stmt = connection.prepareStatement("UPDATE rooms SET name = ? WHERE id = ?");
                            stmt.setString(1, newRoomName);
                            stmt.setInt(2, roomId);
                            stmt.executeUpdate();
                            loadRoomData(connection);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(frame, "Error updating room: " + ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a room to update.");
                }
            });

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error connecting to database: " + ex.getMessage());
        }

        // Set frame visibility
        frame.setVisible(true);
    }

    private void loadRoomData(Connection connection) {
        try {
            tableModel.setRowCount(0); // Clear existing data
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name FROM rooms");
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getInt("id"), rs.getString("name")});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error loading room data: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RoomManagerGUI::new);
    }
}