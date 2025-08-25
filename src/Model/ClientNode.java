package Model;

public class ClientNode {
    Client client;
    ClientNode next;

    public ClientNode(Client client) {
        this.client = client;
        this.next = null;
    }
}
