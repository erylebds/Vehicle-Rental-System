package View;

import Controller.ServerController;

import javax.swing.*;
import java.awt.*;

public class ServerView {
    public static void ServerView() {
        JFrame serverFrame = new JFrame("Vehicle Rental Server");
        serverFrame.setSize(500,500);
        serverFrame.setLocationRelativeTo(null);
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //For displaying activity logs
        JPanel activityLogsPanel = new JPanel();
        activityLogsPanel.setLayout(new BorderLayout());

        JTextArea activityLogs = new JTextArea();
        activityLogs.setEditable(false);
        JScrollPane activityLogsScrollPane = new JScrollPane(activityLogs);

        JPanel buttonPanel = new JPanel();

        //Buttons
        JButton startServerButton = new JButton("Start Server");
        startServerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startServerButton.setPreferredSize(new Dimension(150,50));

        JButton stopServerButton = new JButton("Stop Server");
        stopServerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        stopServerButton.setPreferredSize(new Dimension(150,50));

        //Starts the server when button is toggled
        startServerButton.addActionListener(e -> {
            ServerController.startServer(startServerButton, stopServerButton, activityLogs);;
        });

        stopServerButton.addActionListener(e -> {
            ServerController.stopServer(startServerButton, stopServerButton, activityLogs);
        });

        activityLogsPanel.add(activityLogsScrollPane, BorderLayout.CENTER);
        buttonPanel.add(startServerButton);
        buttonPanel.add(stopServerButton);

        serverFrame.add(activityLogsPanel);
        serverFrame.add(buttonPanel, BorderLayout.SOUTH);
        serverFrame.setVisible(true);
    }
}
