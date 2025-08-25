package Controller;

import Model.Vehicle;
import Model.VehicleLinkedList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdminController {
    private static VehicleLinkedList vehicleLinkedList = new VehicleLinkedList();

    public static void addVehicle(JTable vehicleTable, String image, String type, String model, String plateNumber, String transmission, String gasType, int seatingCapacity, boolean availability) {
        ImageIcon imageIcon = new ImageIcon(image);
        Image scaledImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        vehicleLinkedList.addVehicle(new Vehicle(image, type, model, plateNumber, transmission, gasType, seatingCapacity, availability));
        vehicleLinkedList.saveVehicleToFile();

        ServerController.messageToServer("Vehicle " + type + " " + model + " has been added successfully at " + java.time.LocalDateTime.now());
        JOptionPane.showMessageDialog(null, "Vehicle " + type + " has been added successfully");

        // Only call refreshTable once after adding the vehicle
        refreshTable(vehicleTable);  // Ensure the table is updated immediately
    }

    public static void removeVehicle(String plateNumber, JTable vehicleTable) {
        boolean deleted = vehicleLinkedList.deleteVehicle(plateNumber);

        if (deleted) {
            vehicleLinkedList.saveVehicleToFile();
            ServerController.messageToServer("Vehicle " + plateNumber + " has been deleted successfully at " + java.time.LocalDateTime.now());
            JOptionPane.showMessageDialog(null, "Vehicle " + plateNumber + " has been deleted successfully");

            // Refresh the table after removing the vehicle
            refreshTable(vehicleTable);
        } else {
            JOptionPane.showMessageDialog(null, "Vehicle not found");
        }
    }

    public static void clearVehicleTable(DefaultTableModel vehicleTableModel) {
        vehicleTableModel.setRowCount(0); // This clears the table rows
    }

    public static void listVehicles(DefaultTableModel vehicleTableModel, JTable vehicleTable) {
        // Clear the table before adding new data
        clearVehicleTable(vehicleTableModel);

        VehicleLinkedList vehicleLinkedList = new VehicleLinkedList();
        vehicleLinkedList.loadVehicleFromFile();  // Reload vehicle data from the file
        List<Vehicle> vehicles = vehicleLinkedList.getVehicles();

        for (Vehicle vehicle : vehicles) {
            ImageIcon imageIcon = new ImageIcon(vehicle.getImage());
            Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);

            Object[] rowData = {
                    imageIcon,
                    vehicle.getType(),
                    vehicle.getModel(),
                    vehicle.getPlateNumber(),
                    vehicle.getTransmission(),
                    vehicle.getGasType(),
                    vehicle.getSeatingCapacity(),
                    vehicle.getAvailability() ? "Available" : "Rented"
            };
            vehicleTableModel.addRow(rowData);
        }

        vehicleTable.setRowHeight(100);  // Set row height to match image size
    }

    public static void refreshTable(JTable vehicleTable) {
        DefaultTableModel vehicleTableModel = (DefaultTableModel) vehicleTable.getModel();
        vehicleTableModel.setRowCount(0);  // Clear the previous rows

        List<Vehicle> vehicles = vehicleLinkedList.getVehicles();
        for (Vehicle vehicle : vehicles) {
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
            vehicleTableModel.addRow(rowData);  // Add the updated data
        }
    }

    public static boolean editVehicle(String plateNumber, String image, String type, String model, String transmission, String gasType, int seatingCapacity, boolean availability) {
        Vehicle vehicleToUpdate = VehicleLinkedList.searchVehicle(plateNumber);

        if (vehicleToUpdate != null) {
            vehicleToUpdate.setImage(image);
            vehicleToUpdate.setType(type);
            vehicleToUpdate.setModel(model);
            vehicleToUpdate.setTransmission(transmission);
            vehicleToUpdate.setGasType(gasType);
            vehicleToUpdate.setSeatingCapacity(seatingCapacity);
            vehicleToUpdate.setAvailability(availability);

            return true;
        } else {
            return false;
        }
    }

    public static void getVehicleToEdit(Vehicle vehicleToEdit, JTable vehicleTable) {
        JButton chooseImageButton = new JButton("Choose Image");
        final String[] selectedImagePath = {vehicleToEdit.getImage()};

        //Button Function
        chooseImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Vehicle Image");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                selectedImagePath[0] = file.getAbsolutePath();
            }
        });

        // Text fields
        JTextField typeField = new JTextField(vehicleToEdit.getType(), 15);
        JTextField modelField = new JTextField(vehicleToEdit.getModel(), 15);
        JTextField plateField = new JTextField(vehicleToEdit.getPlateNumber(), 15);
        plateField.setEditable(false);
        plateField.setBackground(Color.LIGHT_GRAY);
        JTextField transmissionField = new JTextField(vehicleToEdit.getTransmission(), 15);
        JTextField gasTypeField = new JTextField(vehicleToEdit.getGasType(), 15);
        JTextField capacityField = new JTextField(String.valueOf(vehicleToEdit.getSeatingCapacity()), 15);

        // UI Panel
        JPanel updateCarPanel = new JPanel(new GridLayout(9, 2)); // 9 rows now
        updateCarPanel.add(new JLabel("Car Image:"));
        updateCarPanel.add(chooseImageButton);
        updateCarPanel.add(new JLabel("Car Type:"));
        updateCarPanel.add(typeField);
        updateCarPanel.add(new JLabel("Car Model:"));
        updateCarPanel.add(modelField);
        updateCarPanel.add(new JLabel("Plate Number:"));
        updateCarPanel.add(plateField);
        updateCarPanel.add(new JLabel("Transmission:"));
        updateCarPanel.add(transmissionField);
        updateCarPanel.add(new JLabel("Gas Type:"));
        updateCarPanel.add(gasTypeField);
        updateCarPanel.add(new JLabel("Capacity:"));
        updateCarPanel.add(capacityField);

        int result = JOptionPane.showConfirmDialog(null, updateCarPanel, "Edit Vehicle", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String image = selectedImagePath[0];
            String type = typeField.getText();
            String model = modelField.getText();
            String plateNumber = plateField.getText();
            String transmission = transmissionField.getText();
            String gasType = gasTypeField.getText();
            String seatingCapacityText = capacityField.getText();

            if (type.isEmpty() || model.isEmpty() || transmission.isEmpty() || gasType.isEmpty() || seatingCapacityText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int seatingCapacity = Integer.parseInt(seatingCapacityText);

                boolean success = editVehicle(plateNumber, image, type, model, transmission, gasType, seatingCapacity, true);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Vehicle updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    ServerController.messageToServer("Vehicle updated successfully! at " + java.time.LocalDateTime.now());
                    VehicleLinkedList.saveVehicleToFile();
                    AdminController.refreshTable(vehicleTable);
                } else {
                    JOptionPane.showMessageDialog(null, "Vehicle update failed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Seating capacity must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void removeDuplicateVehicles(DefaultTableModel vehicleTableModel) {
        Set<String> plateNumbers = new HashSet<>();

        // Iterate over the rows of the table to check for duplicates
        for (int i = 0; i < vehicleTableModel.getRowCount(); i++) {
            String plateNumber = (String) vehicleTableModel.getValueAt(i, 3);  // Plate Number is in column 3
            if (plateNumbers.contains(plateNumber)) {
                vehicleTableModel.removeRow(i);
                i--;
            } else {
                plateNumbers.add(plateNumber);  // Add the plate number to the set
            }
        }
    }
}
