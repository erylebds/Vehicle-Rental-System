package Model;

public class RentalNode {
    Rental rental;
    RentalNode next;

    public RentalNode(Rental rental) {
        this.rental = rental;
        this.next = null;
    }
}
