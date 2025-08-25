import Controller.ServerController;
import View.LoginView;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

public class VehicleRentalSystem {
    private static final ServerController message = new ServerController();
    private static boolean isRunning = true;

    public static void main(String[] args) throws IOException {
        if (isServerRunning(readServerIP())) {
            message.messageToServer("A client has successfully connected to the server"); //Sends a message to the server
            System.out.println("Connected to server at " + readServerIP());
            LoginView.loginView();
            monitorServerActivity(readServerIP());
        } else {
            JOptionPane.showMessageDialog(null, "Server is not running");
        }
    }

    //Reads the IP address of the server
    private static String readServerIP() {
        try(BufferedReader br = new BufferedReader(new FileReader("res/IpConfig.txt"))) {
            return br.readLine(); //Read IP from file
        } catch (IOException e) {
            System.out.println("Failed to read the server's IP address");
            return null;
        }
    }

    //Checks if the server is running
    private static boolean isServerRunning(String ip) {
        try(Socket socket = new Socket(ip, 2025)) {
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Monitor's the status of the server if it's running or not
    private static void monitorServerActivity(String ip) {
        new Thread(() -> {
            while(isRunning) {
                if (!isServerRunning(ip)) {
                    JOptionPane.showMessageDialog(null, "Server is not running. Closing the system...");
                    closeActivities();
                    System.exit(0);
                }

                try {
                    Thread.sleep(5000); //Checks the server's status
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //Closes all activities in the system when server is not working
    public static void closeActivities() {
        for (Window window : Window.getWindows()) {
            window.dispose();
        }
    }
}
