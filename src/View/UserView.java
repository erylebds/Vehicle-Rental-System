package View;

import Controller.LoginRegisterController;
import Controller.UserController;
import Model.Client;

import javax.swing.*;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;

public class UserView {

    public static void userView(Client currentUser) {
        JFrame userFrame = new JFrame("Vehicle Rental User");

        // Components of User Frame
        userFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        userFrame.setLocationRelativeTo(null);
        userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel userPanel = new JPanel(new BorderLayout());

        // Search Panel for Vehicles and Rentals
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchPanel.add(new JLabel("Search Vehicle:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        userPanel.add(searchPanel, BorderLayout.NORTH);
        searchPanel.setVisible(true);

        // For Table
        CardLayout cardLayout = new CardLayout();
        JPanel tablePanel = new JPanel(cardLayout);

        // JTable for Vehicles
        String[] columnHomeTable = {"Picture", "Type", "Model", "Plate Number", "Transmission", "Gas Type", "Seats", "Status"};
        DefaultTableModel vehicleTableModel = new DefaultTableModel(columnHomeTable, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return ImageIcon.class;
                }
                return Object.class;
            }
        };

        JTable vehicleTable = new JTable(vehicleTableModel);
        JScrollPane vehicleScrollPane = new JScrollPane(vehicleTable);
        tablePanel.add(vehicleScrollPane, BorderLayout.CENTER);
        tablePanel.add(vehicleScrollPane, "VehicleTable");

        // Clear the vehicle table before loading new data
        vehicleTableModel.setRowCount(0);  // Clear any existing rows

        // List vehicles once after clearing the table
        UserController.listVehicles(vehicleTableModel, vehicleTable);  // Populate vehicle list
        UserController.removeDuplicateVehicles(vehicleTableModel);
        userPanel.add(tablePanel, BorderLayout.CENTER);

        // JTable for Rentals
        String[] columnRentalsTable = {"Username", "Picture", "Type", "Model", "Plate Number", "Rental Date", "Return Date", "Rental Status"};
        DefaultTableModel rentalTableModel = new DefaultTableModel(columnRentalsTable, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) {
                    return ImageIcon.class;
                }
                return String.class;
            }
        };

        JTable rentalTable = new JTable(rentalTableModel);
        JScrollPane rentalScrollPane = new JScrollPane(rentalTable);
        tablePanel.add(rentalScrollPane, BorderLayout.CENTER);
        tablePanel.add(rentalScrollPane, "RentalTable");

        userPanel.add(tablePanel, BorderLayout.CENTER);

        // List Rentals once on login or after switching views
        UserController.listRentals(rentalTableModel, rentalTable);

        // For Search Bar
        CardLayout searchLayout = (CardLayout) tablePanel.getLayout();
        JPanel searchPanelContainer = new JPanel(searchLayout);
        searchPanelContainer.add(searchPanel, "VehicleSearch");
        userPanel.add(searchPanelContainer, BorderLayout.NORTH);

        // Button Panel for User Functions
        JPanel buttonPanel = new JPanel(new GridLayout(10, 1));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(32, 0, 0, 0));

        // Icons for Buttons
        ImageIcon homeIconFile = new ImageIcon("res/IconsAndImages/homeIcon.png");
        Image homeIconImage = homeIconFile.getImage();
        Image resizeHomeImage = homeIconImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        Icon homeIcon = new ImageIcon(resizeHomeImage);

        ImageIcon rentIconFile = new ImageIcon("res/IconsAndImages/addIcon.png");
        Image rentIconImage = rentIconFile.getImage();
        Image resizeRentImage = rentIconImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        Icon rentIcon = new ImageIcon(resizeRentImage);

        ImageIcon returnIconFile = new ImageIcon("res/IconsAndImages/removeIcon.png");
        Image returnIconImage = returnIconFile.getImage();
        Image resizeReturnImage = returnIconImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        Icon returnIcon = new ImageIcon(resizeReturnImage);

        ImageIcon viewRentalsIconFile = new ImageIcon("res/IconsAndImages/viewIcon.png");
        Image viewRentalsIconImage = viewRentalsIconFile.getImage();
        Image resizeViewRentalsImage = viewRentalsIconImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        Icon viewRentalsIcon = new ImageIcon(resizeViewRentalsImage);

        ImageIcon logoutIconFile = new ImageIcon("res/IconsAndImages/logoutIcon.png");
        Image logoutIconImage = logoutIconFile.getImage();
        Image resizeLogoutImage = logoutIconImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        Icon logoutIcon = new ImageIcon(resizeLogoutImage);

        // Buttons
        JButton homeButton = new JButton(homeIcon);
        JButton rentVehicleButton = new JButton(rentIcon);
        JButton returnVehicleButton = new JButton(returnIcon);
        JButton viewRentalsButton = new JButton(viewRentalsIcon);
        JButton logoutButton = new JButton(logoutIcon);

        // Button Customizations
        customizeButton(homeButton);
        customizeButton(rentVehicleButton);
        customizeButton(returnVehicleButton);
        customizeButton(viewRentalsButton);
        customizeButton(logoutButton);

        // Add Buttons to Panel
        buttonPanel.add(homeButton);
        buttonPanel.add(rentVehicleButton);
        buttonPanel.add(returnVehicleButton);
        buttonPanel.add(viewRentalsButton);
        buttonPanel.add(logoutButton);

        // Button Functions
        // Home Button
        homeButton.addActionListener((ActionEvent e) -> {
            searchLayout.show(searchPanelContainer, "VehicleSearch");
            cardLayout.show(tablePanel, "VehicleTable");
        });

        // Rent Vehicle
        rentVehicleButton.addActionListener((ActionEvent e) -> {
            rentVehicle(userFrame, vehicleTable, currentUser);  // Pass both JFrame and JTable
        });

        // Return Vehicle
        returnVehicleButton.addActionListener((ActionEvent e) -> {
            returnVehicle(userFrame, vehicleTable);  // Pass both JFrame and JTable
        });

        // View Rentals
        viewRentalsButton.addActionListener((ActionEvent e) -> {
            searchLayout.show(searchPanelContainer, "RentalSearch");
            cardLayout.show(tablePanel, "RentalTable");
        });

        // Logout
        logoutButton.addActionListener((ActionEvent e) -> {
            LoginRegisterController.logoutController(userFrame, vehicleTable);
        });

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchField.getText().trim().toLowerCase();
                UserController.filterVehicleTable(vehicleTableModel, vehicleTable, searchText);
            }
        });

        homeButton.doClick(); // Automatically click home button on start-up
        userFrame.add(buttonPanel, BorderLayout.WEST);
        userFrame.add(userPanel);
        userFrame.setVisible(true);
    }

    // Helper method for customizing buttons
    private static void customizeButton(JButton button) {
        button.setPreferredSize(new Dimension(50, 0));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private static void rentVehicle(JFrame userFrame, JTable vehicleTable, Client currentUser) {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow != -1) {
            String plateNumber = vehicleTable.getValueAt(selectedRow, 3).toString();
            String rentStatus = vehicleTable.getValueAt(selectedRow, 7).toString();

            if ("Available".equals(rentStatus)) {
                String type = vehicleTable.getValueAt(selectedRow, 1).toString();
                String model = vehicleTable.getValueAt(selectedRow, 2).toString();

                JTextField usernameField = new JTextField(currentUser.getUsername());
                usernameField.setEditable(false);

                JTextField typeField = new JTextField(type);
                typeField.setEditable(false);

                JTextField modelField = new JTextField(model);
                modelField.setEditable(false);

                JTextField plateField = new JTextField(plateNumber);
                plateField.setEditable(false);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Date today = calendar.getTime();

                // Ensure max rental period is 30 days
                calendar.add(Calendar.DAY_OF_MONTH, 30);
                Date maxAllowedRentDate = calendar.getTime();

                // Set up Rent Date Spinner (no forced correction)
                SpinnerDateModel rentDateModel = new SpinnerDateModel(today, null, null, Calendar.DAY_OF_MONTH);
                JSpinner rentDateSpinner = new JSpinner(rentDateModel);
                JSpinner.DateEditor rentEditor = new JSpinner.DateEditor(rentDateSpinner, "yyyy-MM-dd");
                rentDateSpinner.setEditor(rentEditor);

                // Return Date Spinner (initialized with no restrictions)
                SpinnerDateModel returnDateModel = new SpinnerDateModel(today, null, null, Calendar.DAY_OF_MONTH);
                JSpinner returnDateSpinner = new JSpinner(returnDateModel);
                JSpinner.DateEditor returnEditor = new JSpinner.DateEditor(returnDateSpinner, "yyyy-MM-dd");
                returnDateSpinner.setEditor(returnEditor);

                JPanel rentPanel = new JPanel(new GridBagLayout());
                GridBagConstraints grid = new GridBagConstraints();
                grid.insets = new Insets(5, 5, 5, 5);
                grid.fill = GridBagConstraints.HORIZONTAL;

                grid.gridx = 0;
                grid.gridy = 0;
                rentPanel.add(new JLabel("Username:"), grid);
                grid.gridx = 1;
                rentPanel.add(usernameField, grid);

                grid.gridx = 0;
                grid.gridy = 1;
                rentPanel.add(new JLabel("Vehicle Type:"), grid);
                grid.gridx = 1;
                rentPanel.add(typeField, grid);

                grid.gridx = 0;
                grid.gridy = 2;
                rentPanel.add(new JLabel("Model:"), grid);
                grid.gridx = 1;
                rentPanel.add(modelField, grid);

                grid.gridx = 0;
                grid.gridy = 3;
                rentPanel.add(new JLabel("Plate Number:"), grid);
                grid.gridx = 1;
                rentPanel.add(plateField, grid);

                grid.gridx = 0;
                grid.gridy = 4;
                rentPanel.add(new JLabel("Rent Date [yyyy-MM-dd] (Max: 30 days from today):"), grid);
                grid.gridx = 1;
                rentPanel.add(rentDateSpinner, grid);

                grid.gridx = 0;
                grid.gridy = 5;
                rentPanel.add(new JLabel("Return Date [yyyy-MM-dd] (Must be within 30 days from Rent Date):"), grid);
                grid.gridx = 1;
                rentPanel.add(returnDateSpinner, grid);

                int result = JOptionPane.showConfirmDialog(userFrame, rentPanel, "Enter Rent and Return Dates", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    Date rentDate = (Date) rentDateSpinner.getValue();
                    Date returnDate = (Date) returnDateSpinner.getValue();

                    // Calculate max return date (30 days after rent date)
                    calendar.setTime(rentDate);
                    calendar.add(Calendar.DAY_OF_MONTH, 30);
                    Date maxReturnDate = calendar.getTime();

                    // Validate Rent Date (must be today or later)
                    if (rentDate.before(today)) {
                        JOptionPane.showMessageDialog(userFrame,
                                "Invalid Rent Date!\nRent date cannot be before today. Please select a valid date.",
                                "Date Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Validate Return Date (must be set)
                    if (returnDate == null) {
                        JOptionPane.showMessageDialog(userFrame,
                                "Invalid Return Date!\nPlease select a return date.",
                                "Date Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Validate Return Date (must be at least 1 day after rent date)
                    if (!returnDate.after(rentDate)) {
                        JOptionPane.showMessageDialog(userFrame,
                                "Invalid Return Date!\nThe return date must be at least 1 day after the rent date.",
                                "Date Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Validate Return Date (must not be more than 30 days after rent date)
                    if (returnDate.after(maxReturnDate)) {
                        JOptionPane.showMessageDialog(userFrame,
                                "Invalid Return Date!\nThe return date cannot be more than 30 days after the rent date.",
                                "Date Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // If everything is valid, proceed with renting the vehicle
                    UserController.rentVehicle(userFrame, vehicleTable, currentUser, rentDate, returnDate, plateNumber);
                    UserController.refreshVehicleTable(vehicleTable);
                }
            } else {
                JOptionPane.showMessageDialog(userFrame,
                        "This vehicle is already rented.\nPlease choose an available vehicle.",
                        "Rental Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(userFrame,
                    "No vehicle selected!\nPlease select a vehicle to proceed with the rental.",
                    "Selection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void returnVehicle(JFrame userFrame, JTable vehicleTable) {
        UserController.returnVehicle(userFrame, vehicleTable);

        // Refresh the vehicle table after returning the vehicle
        UserController.refreshVehicleTable(vehicleTable);
    }
}