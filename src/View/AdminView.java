package View;

import Controller.AdminController;
import Controller.LoginRegisterController;
import Controller.UserController;
import Model.Rental;
import Model.RentalLinkedList;
import Model.Vehicle;
import Model.VehicleLinkedList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class AdminView {
    public static void adminView() {
        JFrame adminFrame = new JFrame("Vehicle Rental Admin");

        //Components of Admin Frame
        adminFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel adminPanel = new JPanel(new BorderLayout());

        //Search Panel Components
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchPanel.add(new JLabel("Search Vehicle:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        adminPanel.add(searchPanel, BorderLayout.NORTH);
        searchPanel.setVisible(true);

        //For Table
        CardLayout cardLayout = new CardLayout();
        JPanel tablePanel = new JPanel(cardLayout);

        //JTable Components
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

        //Search Panel View Rentals Components
        JPanel searchPanelRentals = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JTextField searchFieldRentals = new JTextField(15);
        JButton searchButtonRentals = new JButton("Search");
        searchButtonRentals.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchPanelRentals.add(new JLabel("Search Vehicle:"));
        searchPanelRentals.add(searchFieldRentals);
        searchPanelRentals.add(searchButtonRentals);
        adminPanel.add(searchPanelRentals, BorderLayout.NORTH);
        searchPanelRentals.setVisible(false);

        //Rentals Table Components
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

        // Reset the vehicle table to avoid duplicates
        vehicleTableModel.setRowCount(0);  // Clear previous entries

        // List vehicles once on admin view login
        AdminController.listVehicles(vehicleTableModel, vehicleTable);
        AdminController.removeDuplicateVehicles(vehicleTableModel);
        UserController.listRentals(rentalTableModel, rentalTable);
        adminPanel.add(tablePanel, BorderLayout.CENTER);

        //For Search Bar
        CardLayout searchLayout = (CardLayout) tablePanel.getLayout();
        JPanel searchPanelContainer = new JPanel(searchLayout);
        searchPanelContainer.add(searchPanel, "VehicleSearch");
        searchPanelContainer.add(searchPanelRentals, "RentalSearch");
        adminPanel.add(searchPanelContainer, BorderLayout.NORTH);

        //Button Panel for Admin Functions
        JPanel buttonPanel = new JPanel(new GridLayout(10,1));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(32, 0,0,0));

        //Icon for Buttons
        //Home Icon
        ImageIcon homeIconFile = new ImageIcon("res/IconsAndImages/homeIcon.png");
        Image homeIconImage = homeIconFile.getImage();
        Image resizeHomeImage = homeIconImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        Icon homeIcon = new ImageIcon(resizeHomeImage);

        //Add Icon
        ImageIcon addIconFile = new ImageIcon("res/IconsAndImages/addIcon.png");
        Image addIconImage = addIconFile.getImage();
        Image resizeAddImage = addIconImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        Icon addIcon = new ImageIcon(resizeAddImage);

        //Remove Icon
        ImageIcon removeIconFile = new ImageIcon("res/IconsAndImages/removeIcon.png");
        Image removeIconImage = removeIconFile.getImage();
        Image resizeRemoveImage = removeIconImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        Icon removeIcon = new ImageIcon(resizeRemoveImage);

        //Edit Icon
        ImageIcon editIconFile = new ImageIcon("res/IconsAndImages/editIcon.png");
        Image editIconImage = editIconFile.getImage();
        Image resizeEditImage = editIconImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        Icon editIcon = new ImageIcon(resizeEditImage);

        //View Icon
        ImageIcon viewIconFile = new ImageIcon("res/IconsAndImages/viewIcon.png");
        Image viewIconImage = viewIconFile.getImage();
        Image resizeViewImage = viewIconImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        Icon viewIcon = new ImageIcon(resizeViewImage);

        //Notifications Icon
        ImageIcon notificationsIconFile = new ImageIcon("res/IconsAndImages/notificationsIcon.png");
        Image notificationsIconImage = notificationsIconFile.getImage();
        Image resizeNotificationsImage = notificationsIconImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        Icon notificationsIcon = new ImageIcon(resizeNotificationsImage);

        //Messages Icon
        ImageIcon messagesIconFile = new ImageIcon("res/IconsAndImages/messagesIcon.png");
        Image messagesIconImage = messagesIconFile.getImage();
        Image resizeMessagesImage = messagesIconImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        Icon messagesIcon = new ImageIcon(resizeMessagesImage);

        //Profile Icon
        ImageIcon profileIconFile = new ImageIcon("res/IconsAndImages/profileIcon.png");
        Image profileIconImage = profileIconFile.getImage();
        Image resizeProfileImage = profileIconImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        Icon profileIcon = new ImageIcon(resizeProfileImage);

        //Logout Icon
        ImageIcon logoutIconFile = new ImageIcon("res/IconsAndImages/logoutIcon.png");
        Image logoutIconImage = logoutIconFile.getImage();
        Image resizeLogoutImage = logoutIconImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        Icon logoutIcon = new ImageIcon(resizeLogoutImage);

        //Create Buttons for Each Function
        JButton homeButton = new JButton(homeIcon);
        JButton addVehicleButton = new JButton(addIcon);
        JButton removeVehicleButton = new JButton(removeIcon);
        JButton editVehicleButton = new JButton(editIcon);
        JButton viewRentalsButton = new JButton(viewIcon);
        JButton notificationsButton = new JButton(notificationsIcon);
        JButton messagesButton = new JButton(messagesIcon);
        JButton profileButton = new JButton(profileIcon);
        JButton logoutButton = new JButton(logoutIcon);

        //Button Customizations
        //Button Sizes
        homeButton.setPreferredSize(new Dimension(50, 0));
        addVehicleButton.setPreferredSize(new Dimension(50, 0));
        removeVehicleButton.setPreferredSize(new Dimension(50, 0));
        editVehicleButton.setPreferredSize(new Dimension(50, 0));
        viewRentalsButton.setPreferredSize(new Dimension(50, 0));
        notificationsButton.setPreferredSize(new Dimension(50, 0));
        messagesButton.setPreferredSize(new Dimension(50, 0));
        profileButton.setPreferredSize(new Dimension(50, 0));
        logoutButton.setPreferredSize(new Dimension(50, 0));

        //Button Borders
        homeButton.setBorderPainted(false);
        addVehicleButton.setBorderPainted(false);
        removeVehicleButton.setBorderPainted(false);
        editVehicleButton.setBorderPainted(false);
        viewRentalsButton.setBorderPainted(false);
        notificationsButton.setBorderPainted(false);
        messagesButton.setBorderPainted(false);
        profileButton.setBorderPainted(false);
        logoutButton.setBorderPainted(false);

        //Button Fill
        homeButton.setContentAreaFilled(false);
        addVehicleButton.setContentAreaFilled(false);
        removeVehicleButton.setContentAreaFilled(false);
        editVehicleButton.setContentAreaFilled(false);
        viewRentalsButton.setContentAreaFilled(false);
        notificationsButton.setContentAreaFilled(false);
        messagesButton.setContentAreaFilled(false);
        profileButton.setContentAreaFilled(false);
        logoutButton.setContentAreaFilled(false);

        //Button Cursor
        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addVehicleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        removeVehicleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editVehicleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewRentalsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        notificationsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        messagesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //Add Buttons to Panel
        buttonPanel.add(homeButton);
        buttonPanel.add(addVehicleButton);
        buttonPanel.add(removeVehicleButton);
        buttonPanel.add(editVehicleButton);
        buttonPanel.add(viewRentalsButton);
        buttonPanel.add(notificationsButton);
        buttonPanel.add(messagesButton);
        buttonPanel.add(profileButton);
        buttonPanel.add(logoutButton);

        //Button Functions
        //Home
        homeButton.addActionListener((ActionEvent e) -> {
            searchLayout.show(searchPanelContainer, "VehicleSearch");
            cardLayout.show(tablePanel, "VehicleTable");
        });

        //Search Bar
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchField.getText().toLowerCase();
                DefaultTableModel tableModel = (DefaultTableModel) vehicleTable.getModel();
                tableModel.setRowCount(0);  // Clear existing rows

                List<Vehicle> vehicles = VehicleLinkedList.getVehicles(); // Retrieve all vehicles
                for (Vehicle vehicle : vehicles) {
                    // If the plate number matches the search text, add it to the table
                    if (vehicle.getType().toLowerCase().contains(searchText) ||
                            vehicle.getModel().toLowerCase().contains(searchText) ||
                            vehicle.getPlateNumber().toLowerCase().contains(searchText)) {
                        Object[] rowData = {
                                new ImageIcon(new ImageIcon(vehicle.getImage()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)),
                                vehicle.getType(),
                                vehicle.getModel(),
                                vehicle.getPlateNumber(),
                                vehicle.getTransmission(),
                                vehicle.getGasType(),
                                vehicle.getSeatingCapacity(),
                                vehicle.getAvailability() ? "Available" : "Rented"
                        };
                        tableModel.addRow(rowData);
                    }
                }
            }
        });

        //Lets the admin delete a vehicle when a row is double-clicked in the table
        vehicleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = vehicleTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String plateNumber = vehicleTable.getValueAt(selectedRow, 3).toString();

                        int option = JOptionPane.showConfirmDialog(adminFrame,
                                "Do you want to remove this vehicle?", "Remove Vehicle", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            AdminController.removeVehicle(plateNumber, vehicleTable);
                        }
                    }
                }
            }
        });

        //Add
        addVehicleButton.addActionListener((ActionEvent e) -> {
            addVehicles(adminFrame, vehicleTable);
        });

        //Remove
        removeVehicleButton.addActionListener((ActionEvent e) -> {
            int selectedRow = vehicleTable.getSelectedRow();

            if (selectedRow != -1) {
                String selectedVehiclePlateNum = (String) vehicleTable.getValueAt(selectedRow, 3);

                int option = JOptionPane.showConfirmDialog(adminFrame, "Do you want to delete this vehicle?", "Delete Vehicle", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    AdminController.removeVehicle(selectedVehiclePlateNum, vehicleTable);
                }
            } else {
                JOptionPane.showMessageDialog(adminFrame, "Please select a vehicle to delete", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        //Edit
        editVehicleButton.addActionListener((ActionEvent e) -> {
            int selectedRow = vehicleTable.getSelectedRow();
            if (selectedRow != -1) {

                String plateNumber = (String) vehicleTable.getValueAt(selectedRow, 3);

                Vehicle vehicleToUpdate = VehicleLinkedList.searchVehicle(plateNumber);

                if (vehicleToUpdate != null) {
                    AdminController.getVehicleToEdit(vehicleToUpdate, vehicleTable);
                } else {
                    JOptionPane.showMessageDialog(adminFrame, "Vehicle not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(adminFrame, "Please select a vehicle to update.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        //Search Bar Rentals
        searchFieldRentals.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchTextRentals = searchFieldRentals.getText().toLowerCase();
                DefaultTableModel tableModel = (DefaultTableModel) rentalTable.getModel();
                tableModel.setRowCount(0);

                List<Rental> rentals = RentalLinkedList.getRentals();
                for (Rental rental : rentals) {

                    if (rental.getUsername().toLowerCase().contains(searchTextRentals) || rental.getType().toLowerCase().contains(searchTextRentals) ||
                            rental.getModel().toLowerCase().contains(searchTextRentals) ||
                            rental.getPlateNumber().toLowerCase().contains(searchTextRentals)) {
                        Object[] rowData = {
                                rental.getUsername(),
                                new ImageIcon(new ImageIcon(rental.getImage()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)),
                                rental.getType(),
                                rental.getModel(),
                                rental.getPlateNumber(),
                                rental.getRentalDate(),
                                rental.getReturnDate(),
                                rental.getRentalStatus() ? "Returned" : "Not Yet Returned",
                        };
                        tableModel.addRow(rowData);
                    }
                }
            }
        });

        //View
        viewRentalsButton.addActionListener((ActionEvent e) -> {
            searchLayout.show(searchPanelContainer, "RentalSearch");
            cardLayout.show(tablePanel, "RentalTable");
        });

        //Notifications
        //Messages
        //Profile

        //Logout
        logoutButton.addActionListener((ActionEvent e) -> {
            LoginRegisterController.logoutController(adminFrame, vehicleTable);
        });

        cardLayout.show(tablePanel, "VehicleTable"); //Show vehicle table by default
        homeButton.doClick(); //Automatically clicks home button upon start up
        adminFrame.add(buttonPanel, BorderLayout.WEST);
        adminFrame.add(adminPanel);
        adminFrame.setVisible(true);
    }

    private static void addVehicles(JFrame adminFrame, JTable vehicleTable) {
        //Image of Vehicle
        JButton chooseImageButton = new JButton("Choose Image");
        final String[] images = {""};

        //Button Function
        chooseImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("Select Vehicle Image");
            fileChooser.setDialogTitle("Select Vehicle Image");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                images[0] = file.getAbsolutePath();
            }
        });

        //Input Fields for Vehicle Details
        JTextField typeField = new JTextField(10);
        JTextField modelField = new JTextField(10);
        JTextField plateNumberField = new JTextField(10);

        String[] transmissionTypes = {"Manual", "Automatic"};
        JComboBox<String> transmissionTypeComboBox = new JComboBox<>(transmissionTypes);

        String[] fuelTypes = {"Gasoline", "Diesel", "Electric", "Hybrid"};
        JComboBox<String> fuelTypeComboBox = new JComboBox<>(fuelTypes);

        JTextField seatingCapacityField = new JTextField(10);

        //Add Components to Panel
        JPanel addVehiclePanel = new JPanel(new GridBagLayout());
        addVehiclePanel.setPreferredSize(new Dimension(300, 300));
        GridBagConstraints grid = new GridBagConstraints();
        grid.insets = new Insets(5,5,5,5);
        grid.fill = GridBagConstraints.HORIZONTAL;

        grid.gridx = 0;
        grid.gridy = 0;
        addVehiclePanel.add(new JLabel("Image: "), grid);
        grid.gridx = 1;
        addVehiclePanel.add(chooseImageButton, grid);

        grid.gridx = 0;
        grid.gridy = 1;
        addVehiclePanel.add(new JLabel("Car Type:"), grid);
        grid.gridx = 1;
        addVehiclePanel.add(typeField, grid);

        grid.gridx = 0;
        grid.gridy = 2;
        addVehiclePanel.add(new JLabel("Car Model:"), grid);
        grid.gridx = 1;
        addVehiclePanel.add(modelField, grid);

        grid.gridx = 0;
        grid.gridy = 3;
        addVehiclePanel.add(new JLabel("Plate Number:"), grid);
        grid.gridx = 1;
        addVehiclePanel.add(plateNumberField, grid);

        grid.gridx = 0;
        grid.gridy = 4;
        addVehiclePanel.add(new JLabel("Transmission:"), grid);
        grid.gridx = 1;
        addVehiclePanel.add(transmissionTypeComboBox, grid);

        grid.gridx = 0;
        grid.gridy = 5;
        addVehiclePanel.add(new JLabel("Fuel Type:"), grid);
        grid.gridx = 1;
        addVehiclePanel.add(fuelTypeComboBox, grid);

        grid.gridx = 0;
        grid.gridy = 6;
        addVehiclePanel.add(new JLabel("Seating Capacity:"), grid);
        grid.gridx = 1;
        addVehiclePanel.add(seatingCapacityField, grid);

        int result = JOptionPane.showConfirmDialog(adminFrame, addVehiclePanel, "Add Vehicle", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String type = typeField.getText();
            String model = modelField.getText();
            String plateNumber = plateNumberField.getText();
            String transmission = (String) transmissionTypeComboBox.getSelectedItem();
            String fuelType = (String) fuelTypeComboBox.getSelectedItem();
            String seatingCapacity = seatingCapacityField.getText();

            if (type.isEmpty() || model.isEmpty() || plateNumber.isEmpty() || seatingCapacity.isEmpty()) {
                JOptionPane.showMessageDialog(adminFrame, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                AdminController.addVehicle(vehicleTable, images[0], type, model, plateNumber, transmission, fuelType, Integer.parseInt(seatingCapacity), true);
                System.out.println("Vehicle Added");
            }
        }
    }
}
