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

public class VehicleLinkedList {
    private static VehicleNode head;

    public static void addVehicle(Vehicle vehicle) {
        VehicleNode newNode = new VehicleNode(vehicle);

        if (head == null) {
            head = newNode; //Set new node as head
        } else {
            VehicleNode temp = head;
            while (temp.next != null) {
                temp = temp.next; //Move to the last node
            }
            temp.next = newNode; //Adds new node at the end
        }
    }

    public static boolean deleteVehicle(String plateNumber) {
        if (head == null) { return false; } //List is empty

        if (head.vehicle.getPlateNumber().equalsIgnoreCase(plateNumber)) {
            head = head.next;
            return true;
        }

        VehicleNode temp = head;
        while (temp.next != null) {
            if (temp.next.vehicle.getPlateNumber().equalsIgnoreCase(plateNumber)) {
                temp.next = temp.next.next;
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public static Vehicle searchVehicle(String plateNumber) {
        if (plateNumber == null) return null;

        plateNumber = plateNumber.trim().toUpperCase();

        for (Vehicle vehicle : getVehicles()) {
            if (vehicle.getPlateNumber().trim().toUpperCase().equals(plateNumber)) {
                return vehicle;
            }
        }
        return null;
    }

    public static List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        VehicleNode temp = head;

        while (temp != null) {
            vehicles.add(temp.vehicle);
            temp = temp.next;
        }
        return vehicles;
    }

    public static void saveVehicleToFile() {
        try {
            File file = new File("res/XmlFiles/Vehicles.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
            Document doc = documentBuilder.newDocument();

            Element root = doc.createElement("Vehicles");
            doc.appendChild(root);

            VehicleNode temp = head;

            while (temp != null) {
                Element vehicle = doc.createElement("Vehicle");

                Element image = doc.createElement("Image");
                image.appendChild(doc.createTextNode(temp.vehicle.getImage()));
                vehicle.appendChild(image);

                Element type = doc.createElement("Type");
                type.appendChild(doc.createTextNode(temp.vehicle.getType()));
                vehicle.appendChild(type);

                Element model = doc.createElement("Model");
                model.appendChild(doc.createTextNode(temp.vehicle.getModel()));
                vehicle.appendChild(model);

                Element plateNumber = doc.createElement("PlateNumber");
                plateNumber.appendChild(doc.createTextNode(temp.vehicle.getPlateNumber()));
                vehicle.appendChild(plateNumber);

                Element transmission = doc.createElement("Transmission");
                transmission.appendChild(doc.createTextNode(temp.vehicle.getTransmission()));
                vehicle.appendChild(transmission);

                Element gasType = doc.createElement("GasType");
                gasType.appendChild(doc.createTextNode(temp.vehicle.getGasType()));
                vehicle.appendChild(gasType);

                Element seatingCapacity = doc.createElement("SeatingCapacity");
                seatingCapacity.appendChild(doc.createTextNode(String.valueOf(temp.vehicle.getSeatingCapacity())));
                vehicle.appendChild(seatingCapacity);

                Element availibility = doc.createElement("Availability");
                availibility.appendChild(doc.createTextNode(String.valueOf(temp.vehicle.getAvailability())));
                vehicle.appendChild(availibility);

                root.appendChild(vehicle);
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

    public void loadVehicleFromFile() {
        try {
            File file = new File("res/XmlFiles/Vehicles.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("Vehicle");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String image = eElement.getElementsByTagName("Image").item(0).getTextContent();
                    String type = eElement.getElementsByTagName("Type").item(0).getTextContent();
                    String model = eElement.getElementsByTagName("Model").item(0).getTextContent();
                    String plateNumber = eElement.getElementsByTagName("PlateNumber").item(0).getTextContent();
                    String transmission = eElement.getElementsByTagName("Transmission").item(0).getTextContent();
                    String gasType = eElement.getElementsByTagName("GasType").item(0).getTextContent();
                    int seatingCapacity = Integer.parseInt(eElement.getElementsByTagName("SeatingCapacity").item(0).getTextContent());
                    boolean availability = Boolean.parseBoolean(eElement.getElementsByTagName("Availability").item(0).getTextContent());

                    Vehicle vehicle = new Vehicle(image, type, model, plateNumber, transmission, gasType, seatingCapacity, availability);
                    this.addVehicle(vehicle);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
