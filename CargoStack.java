package airlineinventory;

import java.util.ArrayList;
import java.util.List;

public class CargoStack {
    private List<CargoItem> stackList;
    private int capacity;

    public CargoStack(int capacity) {
        this.capacity = capacity;
        this.stackList = new ArrayList<>();
    }

    // CREATE: Push operation
    public boolean push(CargoItem item) {
        if (stackList.size() >= capacity) {
            return false; // Stack Overflow
        }
        stackList.add(item); 
        return true;
    }

    // DELETE: Pop top operation
    public CargoItem pop() {
        if (isEmpty()) {
            return null; // Stack Underflow
        }
        return stackList.remove(stackList.size() - 1);
    }

    // DELETE: Remove specific item by ID ("Delete for one")
    public boolean removeById(String cargoId) {
        for (int i = 0; i < stackList.size(); i++) {
            if (stackList.get(i).getCargoId().equals(cargoId)) {
                stackList.remove(i);
                return true;
            }
        }
        return false;
    }

    // READ: Get all elements for display (top to bottom)
    public List<CargoItem> getElements() {
        List<CargoItem> reversed = new ArrayList<>();
        for (int i = stackList.size() - 1; i >= 0; i--) {
            reversed.add(stackList.get(i));
        }
        return reversed;
    }

    // UPDATE: Modify item by cargoId
    public boolean update(String cargoId, String newDest, double newWeight, String newOwner) {
        for (CargoItem item : stackList) {
            if (item.getCargoId().equals(cargoId)) {
                item.setDestination(newDest);
                item.setWeight(newWeight);
                item.setPassengerName(newOwner);
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return stackList.isEmpty();
    }

    public int size() {
        return stackList.size();
    }
}