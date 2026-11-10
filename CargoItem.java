package airlineinventory;

public class CargoItem {
    private String cargoId;
    private String destination;
    private double weight;
    private String passengerName;

    public CargoItem(String cargoId, String destination, double weight, String passengerName) {
        this.cargoId = cargoId;
        this.destination = destination;
        this.weight = weight;
        this.passengerName = passengerName;
    }

    public String getCargoId() { return cargoId; }
    public void setCargoId(String cargoId) { this.cargoId = cargoId; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    @Override
    public String toString() {
        return "ID: " + cargoId + " | Dest: " + destination + " | Wt: " + weight + "kg | Owner: " + passengerName;
    }
}