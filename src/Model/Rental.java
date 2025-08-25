package Model;

public class Rental {
    String username, image, type, model, plateNumber, rentalDate, returnDate;
    boolean rentalStatus;

    public Rental(String username, String image, String type, String model, String plateNumber, String rentalDate, String returnDate, boolean rentalStatus) {
        this.username = username;
        this.image = image;
        this.type = type;
        this.model = model;
        this.plateNumber = plateNumber;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.rentalStatus = rentalStatus;
    }

    public String getUsername() {
        return username;
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

    public String getRentalDate() {
        return rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public boolean getRentalStatus() {
        return rentalStatus;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setRentalStatus(boolean rentalStatus) {
        this.rentalStatus = rentalStatus;
    }
}
