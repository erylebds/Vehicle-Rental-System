package Controller;

import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserController {

    // Rent a vehicle
    public static void rentVehicle(JFrame userFrame, JTable vehicleTable, Client currentUser, Date rentDate, Date returnDate, String plateNumber) {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow != -1) {
            String rentStatus = vehicleTable.getValueAt(selectedRow, 7).toString();

            if ("Available".equals(rentStatus)) {
                // Validate both dates
                if (rentDate.before(returnDate)) {
                    Vehicle vehicle = VehicleLinkedList.searchVehicle(plateNumber);
                    String imagePath = vehicle.getImage();  // Get the image path from the vehicle object

                    // Convert Date to String format for rental
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String rentDateStr = dateFormat.format(rentDate);  // Convert rentDate to string
                    String returnDateStr = dateFormat.format(returnDate);  // Convert returnDate to string

                    // Create rental object
                    Rental rental = new Rental(currentUser.getUsername(), imagePath, vehicle.getType(), vehicle.getModel(), plateNumber, rentDateStr, returnDateStr, true);

                    // Add rental to the RentalLinkedList and save it
                    RentalLinkedList.addRent(rental);
                    RentalLinkedList.saveRentalToFile();

                    // Update the vehicle status to "Rented" in the table
                    vehicleTable.setValueAt("Rented", selectedRow, 7);  // Directly update the status in the selected row

                    // Update vehicle availability in the vehicle list
                    vehicle.setAvailability(false);
                    VehicleLinkedList.saveVehicleToFile();  // Save the updated vehicle status

                    JOptionPane.showMessageDialog(userFrame, "Vehicle rented successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(userFrame, "Return date cannot be earlier than rental date.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(userFrame, "This vehicle is already rented.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(userFrame, "Please select a vehicle to rent.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method to check if return date is after rental date
    public static boolean isReturnDateAfterRentDate(Date rentDate, Date returnDate) {
        // Directly compare the Date objects
        return !returnDate.before(rentDate);  // return true if returnDate is after rentDate
    }

    // Helper method to validate the date format (YYYY-MM-DD)
    public static boolean isValidDate(String dateStr) {
        // Define the date format you want
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);  // Set lenient to false to ensure strict parsing
        try {
            // Try parsing the date
            dateFormat.parse(dateStr);
            return true;  // If successfully parses, it's a valid date
        } catch (ParseException e) {
            return false;  // If parsing fails, it's not a valid date
        }
    }

    // Method to clear vehicle table before loading data
    public static void clearVehicleTable(DefaultTableModel vehicleTableModel) {
        vehicleTableModel.setRowCount(0);  // Clear the table
    }

    // Modify the listVehicles method to ensure it only adds data once
    public static void listVehicles(DefaultTableModel vehicleTableModel, JTable vehicleTable) {
        vehicleTableModel.setRowCount(0);  // Clear previous rows before adding new data

        // Load data
        VehicleLinkedList vehicleLinkedList = new VehicleLinkedList();
        vehicleLinkedList.loadVehicleFromFile();
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

    // Refresh the vehicle table after rental or return
    public static void refreshVehicleTable(JTable vehicleTable) {
        DefaultTableModel vehicleTableModel = (DefaultTableModel) vehicleTable.getModel();

        // Refresh the table view by triggering a data refresh
        vehicleTableModel.fireTableDataChanged(); // Forces a refresh of the table view.
    }

    // View the user's rentals
    public static void listRentals(DefaultTableModel rentalTableModel, JTable rentalTable) {
        RentalLinkedList rentalLinkedList = new RentalLinkedList();  // Create an instance
        rentalLinkedList.loadRentalsFromFile();  // Call the instance method
        rentalTableModel.setRowCount(0); // Clear previous data

        List<Rental> rentals = rentalLinkedList.getRentals();

        for (Rental rental : rentals) {
            ImageIcon imageIcon = new ImageIcon(rental.getImage());
            Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);

            Object[] rowData = {
                    rental.getUsername(),
                    imageIcon,
                    rental.getType(),
                    rental.getModel(),
                    rental.getPlateNumber(),
                    rental.getRentalDate(),
                    rental.getReturnDate(),
                    rental.getRentalStatus() ? "Returned" : "Not Yet Returned"
            };
            rentalTableModel.addRow(rowData);
        }
        rentalTable.setRowHeight(100);
    }

    // Return a rented vehicle
    public static void returnVehicle(JFrame userFrame, JTable vehicleTable) {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow != -1) {
            String plateNumber = vehicleTable.getValueAt(selectedRow, 3).toString();
            String rentStatus = vehicleTable.getValueAt(selectedRow, 7).toString();

            if ("Rented".equals(rentStatus)) {
                int option = JOptionPane.showConfirmDialog(userFrame, "Do you want to return this vehicle?", "Return Vehicle", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    // Update the rental status in the RentalLinkedList
                    Rental rental = RentalLinkedList.searchRent(plateNumber);
                    if (rental != null) {
                        rental.setRentalStatus(false); // Mark the rental as returned
                        RentalLinkedList.saveRentalToFile(); // Save the updated rental data to XML
                    }

                    // Update the vehicle availability in the VehicleLinkedList
                    Vehicle vehicle = VehicleLinkedList.searchVehicle(plateNumber);
                    if (vehicle != null) {
                        vehicle.setAvailability(true); // Mark the vehicle as available
                        VehicleLinkedList.saveVehicleToFile(); // Save the updated vehicle data to XML
                    }

                    vehicleTable.setValueAt("Available", selectedRow, 7); // Change the rental status to "Available"

                    JOptionPane.showMessageDialog(userFrame, "Vehicle returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(userFrame, "This vehicle is not rented.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(userFrame, "Please select a vehicle to return.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Refresh the rental table after a vehicle return
    public static void refreshRentalTable(JTable rentalTable) {
        DefaultTableModel rentalTableModel = (DefaultTableModel) rentalTable.getModel();
        rentalTableModel.setRowCount(0);  // Clear previous data

        // Reload rental data from file
        RentalLinkedList rentalLinkedList = new RentalLinkedList();
        rentalLinkedList.loadRentalsFromFile();  // Reload updated rentals
        List<Rental> rentals = rentalLinkedList.getRentals();

        for (Rental rental : rentals) {
            ImageIcon imageIcon = new ImageIcon(rental.getImage());
            Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);

            Object[] rowData = {
                    rental.getUsername(),
                    imageIcon,
                    rental.getType(),
                    rental.getModel(),
                    rental.getPlateNumber(),
                    rental.getRentalDate(),
                    rental.getReturnDate(),
                    rental.getRentalStatus() ? "Returned" : "Not Yet Returned"
            };
            rentalTableModel.addRow(rowData);
        }
        rentalTable.setRowHeight(100);
    }

    public static void removeDuplicateVehicles(DefaultTableModel vehicleTableModel) {
        Set<String> plateNumbers = new HashSet<>();

        // Iterate over the rows of the table to check for duplicates
        for (int i = 0; i < vehicleTableModel.getRowCount(); i++) {
            String plateNumber = (String) vehicleTableModel.getValueAt(i, 3);  // Assuming Plate Number is in column 3
            if (plateNumbers.contains(plateNumber)) {
                vehicleTableModel.removeRow(i);
                i--;
            } else {
                plateNumbers.add(plateNumber);  // Add the plate number to the set
            }
        }
    }

    // Method to filter the vehicle table based on search input
    public static void filterVehicleTable(DefaultTableModel tableModel, JTable vehicleTable, String searchText) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        vehicleTable.setRowSorter(sorter);

        if (searchText.isEmpty()) {
            sorter.setRowFilter(null); // Show all rows if search is empty
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 1, 2, 3)); // Filter by Type, Model, or Plate Number
        }
    }
}