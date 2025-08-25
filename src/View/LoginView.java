package View;

import Controller.LoginRegisterController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView {
    public static void loginView() {
        JFrame loginFrame = new JFrame("Vehicle Rental Login"); //Login Frame

        //Components of Login Frame
        loginFrame.setSize(300, 300);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Alignment of loginFrame components
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints loginGrid = new GridBagConstraints();
        loginGrid.insets = new Insets(5,5,5,5);
        loginGrid.fill = GridBagConstraints.HORIZONTAL;
        loginGrid.anchor = GridBagConstraints.CENTER;

        //Login Detail Input Components

        //Username Text Field
        loginGrid.gridx = 0;
        loginGrid.gridy = 0;
        loginPanel.add(new JLabel("Username:"), loginGrid);
        loginGrid.gridx = 1;
        JTextField usernameField = new JTextField(20);
        loginPanel.add(usernameField, loginGrid);

        //Password Text Field
        loginGrid.gridx = 0;
        loginGrid.gridy = 1;
        loginPanel.add(new JLabel("Password:"), loginGrid);
        loginGrid.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        loginPanel.add(passwordField, loginGrid);

        //Login Button
        loginGrid.gridx = 1;
        loginGrid.gridy = 2;
        loginGrid.fill = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginPanel.add(loginButton, loginGrid);

        //Register Link
        loginGrid.gridy = 3;
        loginGrid.fill = GridBagConstraints.CENTER;
        JLabel registerLink = new JLabel("Register an Account");
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginPanel.add(registerLink, loginGrid);

        //Add Components to Login Frame
        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);

        loginButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginFrame, "Please enter a valid username/password.");
            } else if (!username.isEmpty() && !password.isEmpty()) {
                LoginRegisterController.loginController(usernameField.getText(), String.valueOf(passwordField.getPassword()));
                loginFrame.dispose(); //Closes login frame
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid Credentials. Please try again.");
            }
        });

        registerLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterView.registerView(); //Directs the client to register window
                loginFrame.dispose(); //Closes login frame
            }
        });
    }
}
