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
import java.util.ArrayList;
import java.util.List;

public class RentalLinkedList {
    private static RentalNode head;

    public static void addRent(Rental rental) {
        RentalNode newNode = new RentalNode(rental);

        if (head == null) {
            head = newNode; //Set new node as head
        } else {
            RentalNode temp = head;
            while (temp.next != null) {
                temp = temp.next; //Move to the last node
            }
            temp.next = newNode; //Adds new node at the end
        }
    }

    public static boolean deleteRent(String plateNumber) {
        if (head == null) { return false; } //List is empty

        if (head.rental.getPlateNumber().equalsIgnoreCase(plateNumber)) {
            head = head.next;
            return true;
        }

        RentalNode temp = head;
        while (temp.next != null) {
            if (temp.next.rental.getPlateNumber().equalsIgnoreCase(plateNumber)) {
                temp.next = temp.next.next;
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public static Rental searchRent(String plateNumber) {
        if (plateNumber == null) return null;

        plateNumber = plateNumber.trim().toUpperCase();

        for (Rental rental : getRentals()) {
            if (rental.getPlateNumber().trim().toUpperCase().equals(plateNumber)) {
                return rental;
            }
        }
        return null;
    }

    public static List<Rental> getRentals() {
        List<Rental> rentals = new ArrayList<>();
        RentalNode temp = head;

        while (temp != null) {
            rentals.add(temp.rental);
            temp = temp.next;
        }
        return rentals;
    }

    public static void saveRentalToFile() {
        try {
            File file = new File("res/XmlFiles/Rentals.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
            Document doc = documentBuilder.newDocument();

            Element root = doc.createElement("Rentals");
            doc.appendChild(root);

            RentalNode temp = head;

            while (temp != null) {
                Element rental = doc.createElement("Rental");

                Element username = doc.createElement("Username");
                username.appendChild(doc.createTextNode(temp.rental.getUsername()));
                rental.appendChild(username);

                Element image = doc.createElement("Image");
                image.appendChild(doc.createTextNode(temp.rental.getImage()));
                rental.appendChild(image);

                Element type = doc.createElement("Type");
                type.appendChild(doc.createTextNode(temp.rental.getType()));
                rental.appendChild(type);

                Element model = doc.createElement("Model");
                model.appendChild(doc.createTextNode(temp.rental.getModel()));
                rental.appendChild(model);

                Element plateNumber = doc.createElement("PlateNumber");
                plateNumber.appendChild(doc.createTextNode(temp.rental.getPlateNumber()));
                rental.appendChild(plateNumber);

                Element rentalDate = doc.createElement("RentalDate");
                rentalDate.appendChild(doc.createTextNode(temp.rental.getRentalDate()));
                rental.appendChild(rentalDate);

                Element returnDate = doc.createElement("ReturnDate");
                returnDate.appendChild(doc.createTextNode(temp.rental.getReturnDate()));
                rental.appendChild(returnDate);

                Element rentalStatus = doc.createElement("RentalStatus");
                rentalStatus.appendChild(doc.createTextNode(String.valueOf(temp.rental.getRentalStatus())));
                rental.appendChild(rentalStatus);

                root.appendChild(rental);
                temp = temp.next; //Move to the next vehicle
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadRentalsFromFile() {
        try {
            File file = new File("res/XmlFiles/Rentals.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("Rental");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String username = eElement.getElementsByTagName("Username").item(0).getTextContent();
                    String image = eElement.getElementsByTagName("Image").item(0).getTextContent();
                    String type = eElement.getElementsByTagName("Type").item(0).getTextContent();
                    String model = eElement.getElementsByTagName("Model").item(0).getTextContent();
                    String plateNumber = eElement.getElementsByTagName("PlateNumber").item(0).getTextContent();
                    String rentalDate = eElement.getElementsByTagName("RentalDate").item(0).getTextContent();
                    String returnDate = eElement.getElementsByTagName("ReturnDate").item(0).getTextContent();
                    boolean rentalStatus = Boolean.parseBoolean(eElement.getElementsByTagName("RentalStatus").item(0).getTextContent());

                    Rental rental = new Rental(username, image, type, model, plateNumber, rentalDate, returnDate, rentalStatus);
                    this.addRent(rental);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
