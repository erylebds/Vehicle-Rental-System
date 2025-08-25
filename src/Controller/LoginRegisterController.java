package Controller;

import Model.Client;
import Model.ClientLinkedList;
import View.AdminView;
import View.LoginView;
import View.UserView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;

public class LoginRegisterController {
    private static ClientLinkedList clientLinkedList = new ClientLinkedList();

    //Opens client page
    public static void loginController(String username, String password) {
        String clientRole = clientLinkedList.verifyClient(username, password);

        ServerController.messageToServer("Client: " + username + " logged in successfully. Logged in as: " + clientRole);

        if (clientRole != null) {
            // Fetch current user's details after login
            Client currentUser = ClientLinkedList.getClientByUsername(username);
            if (clientRole.equals("Admin")) {
                AdminView.adminView();
            } else if (clientRole.equals("User")) {
                UserView.userView(currentUser);
            }
        }
    }

    //Registers the account and saves the data inside the xml file
    public static void registerController(String username, String password, String role) {
        clientLinkedList.loadClientFromFile();
        clientLinkedList.addClient(new Client(username, password, role));
        clientLinkedList.saveClientToFile();

        ServerController.messageToServer("A new client " + username + " has been registered. Role: " + role);

        JOptionPane.showMessageDialog(null, "Account Registered Successfully.");
        LoginView.loginView();
    }

    public static void logoutController(JFrame userFrame, JTable vehicleTable) {
        // Clear the vehicle table before navigating away
        DefaultTableModel vehicleTableModel = (DefaultTableModel) vehicleTable.getModel();
        vehicleTableModel.setRowCount(0);  // Clear the existing table data

        // Clear vehicle data from the model or reset it to an initial state
        UserController.clearVehicleTable(vehicleTableModel);
        AdminController.clearVehicleTable(vehicleTableModel);

        // Dispose of the current user frame and return to the login screen
        userFrame.dispose();

        // Navigate to the login view
        LoginView.loginView();
    }
}
