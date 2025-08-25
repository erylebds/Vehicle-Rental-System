package Model;

public class Vehicle {
    private String image, type, model, plateNumber, transmission, gasType;
    private int seatingCapacity;
    private boolean availability;

    public Vehicle(String image, String type, String model, String plateNumber, String transmission, String gasType, int seatingCapacity, boolean availability) {
        this.image = image;
        this.type = type;
        this.model = model;
        this.plateNumber = plateNumber;
        this.transmission = transmission;
        this.gasType = gasType;
        this.seatingCapacity = seatingCapacity;
        this.availability = availability;
    }

    public String getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getGasType() {
        return gasType;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public void setGasType(String gasType) {
        this.gasType = gasType;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
