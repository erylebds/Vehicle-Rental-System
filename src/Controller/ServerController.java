package Controller;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerController {
    private static final int port = 2025;
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);
    private static ServerSocket serverSocket;
    private static boolean isRunning = false;

    //Starts server operations
    public static void startServer(JButton startButton, JButton stopButton, JTextArea activityLogs) {
        if (isRunning) return;
        isRunning = true;

        new Thread(() -> {
            try {
                InetAddress address = InetAddress.getLocalHost();
                String ip = address.getHostAddress();

                try (FileWriter writer = new FileWriter("res/IpConfig.txt")) {
                    writer.write(ip);
                }

                serverSocket = new ServerSocket(port, 50, InetAddress.getByName("0.0.0.0"));
                displayActivityLogs(activityLogs, "Server is running on " + address.getHostAddress() + ":" + port);

                SwingUtilities.invokeLater(() -> {
                    startButton.setEnabled(false);
                    stopButton.setEnabled(true);
                });

                while (isRunning) {
                    try {
                        Socket socket = serverSocket.accept();
                        pool.execute(() -> client(socket, activityLogs));
                    } catch (IOException e) {
                        if (!isRunning) {
                            displayActivityLogs(activityLogs, " Server is closed");
                        } else {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void client(Socket socket, JTextArea activityLogs) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            String request = reader.readLine();

            if (request != null) {
                writer.println("Server received request: " + request);
                saveActivityLogsToFile(request);
                displayActivityLogs(activityLogs, "Server received request: " + request + " at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Stops the server's operations
    public static void stopServer(JButton startButton, JButton stopButton, JTextArea activityLogs) {
        if (!isRunning) return;
        isRunning = false;

        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                displayActivityLogs(activityLogs, " Closing server...");
                serverSocket.close();
            }
            pool.shutdown();

            SwingUtilities.invokeLater(() -> {
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Sends a message request to the server
    public static void messageToServer(String message) {
        try (BufferedReader br = new BufferedReader(new FileReader("res/IpConfig.txt"));
             Socket socket = new Socket(br.readLine(), 2025)){
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Save activity logs to a text file
    private static void saveActivityLogsToFile(String request) {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        String entries = timeStamp + " - Server request received: " + request + "\n";

        try (FileWriter writeActivityLogs = new FileWriter("9334-team02_preproject-main/res/ServerActivityLogs.txt", true);
             BufferedWriter writer = new BufferedWriter(writeActivityLogs)) {

            writer.write(entries);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Display activity logs the server window
    private static void displayActivityLogs(JTextArea activityLogs, String message) {
        SwingUtilities.invokeLater(() -> {
            activityLogs.append(message + "\n");
            activityLogs.setCaretPosition(activityLogs.getDocument().getLength());
        });
    }
}