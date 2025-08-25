package Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class ClientLinkedList {
    private static ClientNode head;

    public static Client getClientByUsername(String username) {
        ClientNode temp = head;
        while (temp != null) {
            if (temp.client.getUsername().equals(username)) {
                return temp.client; // Return matching client
            }
            temp = temp.next;
        }
        return null;    // No client found
    }

    public void addClient(Client client) {
        ClientNode newNode = new ClientNode(client);

        if (head == null) {
            head = newNode; //Set new node as head
        } else {
            ClientNode temp = head;
            while (temp.next != null) {
                temp = temp.next; //Move to the last node
            }
            temp.next = newNode; //Adds new node at the end
        }
    }

    public String verifyClient(String username, String password) {
        loadClientFromFile();
        ClientNode temp = head;
        while (temp != null) {
            if (temp.client.getUsername().equals(username) && temp.client.getPassword().equals(password)) {
                System.out.println("Login successful! User: " + temp.client.getUsername() + " Role: " + temp.client.getRole());
                return temp.client.getRole();
            }
            temp = temp.next; //Move to the next node
        }
        System.out.println("Login failed!");
        return null; //Return null if the credentials do not match
    }

    public void saveClientToFile() {
        try {
            File file = new File("res/XmlFiles/Clients.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("Clients");
            document.appendChild(rootElement);

            ClientNode temp = head;

            while (temp != null) {
                Element clientElement = document.createElement("Client");

                Element usernameElement = document.createElement("Username");
                usernameElement.appendChild(document.createTextNode(temp.client.getUsername()));
                clientElement.appendChild(usernameElement);

                Element passwordElement = document.createElement("Password");
                passwordElement.appendChild(document.createTextNode(temp.client.getPassword()));
                clientElement.appendChild(passwordElement);

                Element roleElement = document.createElement("Role");
                roleElement.appendChild(document.createTextNode(temp.client.getRole()));
                clientElement.appendChild(roleElement);

                rootElement.appendChild(clientElement);
                temp = temp.next; //Move to the next client
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadClientFromFile() {
        try {
            File file = new File("res/XmlFiles/Clients.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("Client");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element clientElement = (Element) nNode;

                    String username = clientElement.getElementsByTagName("Username").item(0).getTextContent();
                    String password = clientElement.getElementsByTagName("Password").item(0).getTextContent();
                    String role = clientElement.getElementsByTagName("Role").item(0).getTextContent();

                    Client client = new Client(username, password, role);
                    this.addClient(client);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
