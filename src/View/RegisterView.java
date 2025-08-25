package View;

import Controller.LoginRegisterController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterView {
    public static void registerView() {
        JFrame registerFrame = new JFrame("Vehicle Rental Register Account"); //Register Frame

        //Components of Register Frame
        registerFrame.setSize(300, 300);
        registerFrame.setLocationRelativeTo(null);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Alignment of registerFrame components
        JPanel registerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints registerGrid = new GridBagConstraints();
        registerGrid.insets = new Insets(5,5,5,5);
        registerGrid.fill = GridBagConstraints.HORIZONTAL;
        registerGrid.anchor = GridBagConstraints.CENTER;

        //Register Detail Input Components

        //Username Text Field
        registerGrid.gridx = 0;
        registerGrid.gridy = 0;
        registerPanel.add(new JLabel("Username:"), registerGrid);
        registerGrid.gridx = 1;
        JTextField usernameField = new JTextField(20);
        registerPanel.add(usernameField, registerGrid);

        //Password Text Field
        registerGrid.gridx = 0;
        registerGrid.gridy = 1;
        registerPanel.add(new JLabel("Password:"), registerGrid);
        registerGrid.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        registerPanel.add(passwordField, registerGrid);

        //Register Button
        registerGrid.gridx = 1;
        registerGrid.gridy = 2;
        registerGrid.fill = GridBagConstraints.CENTER;
        JButton registerButton = new JButton("Register");
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerPanel.add(registerButton, registerGrid);

        //Login Link
        registerGrid.gridy = 3;
        registerGrid.fill = GridBagConstraints.CENTER;
        JLabel loginLink = new JLabel("Login");
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerPanel.add(loginLink, registerGrid);

        //Add Components to Register Frame
        registerFrame.add(registerPanel);
        registerFrame.setVisible(true);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String role = "User"; //Sets the default role of the client
            //Change admin role manually in xml file

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(registerFrame, "Please enter a valid username/password");
            } else {
                LoginRegisterController.registerController(username, password, role);
                registerFrame.dispose();
            }
        });

        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginView.loginView(); //Directs client to login window
                registerFrame.dispose(); //Closes register frame
            }
        });
    }
}
