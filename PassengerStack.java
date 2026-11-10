package airlineinventory;

import java.util.ArrayList;
import java.util.List;

public class PassengerStack {
    private List<PassengerItem> stackList;
    private int capacity;

    public PassengerStack(int capacity) {
        this.capacity = capacity;
        this.stackList = new ArrayList<>();
        // Pre-load sample items matching the screenshot
        stackList.add(new PassengerItem("P01", "Ayesha Khan", "Karachi", "923001234567", "20", "Female"));
        stackList.add(new PassengerItem("P02", "Ali Raza", "Lahore", "923004567890", "24", "Male"));
        stackList.add(new PassengerItem("P03", "Sara Ahmed", "Islamabad", "923009876543", "22", "Female"));
    }

    // CREATE: Push operation
    public boolean push(PassengerItem item) {
        if (stackList.size() >= capacity) {
            return false; // Stack Overflow
        }
        stackList.add(item);
        return true;
    }

    // DELETE: Pop top operation
    public PassengerItem pop() {
        if (isEmpty()) {
            return null; // Stack Underflow
        }
        return stackList.remove(stackList.size() - 1);
    }

    // DELETE: Remove specific passenger by ID
    public boolean removeById(String pId) {
        for (int i = 0; i < stackList.size(); i++) {
            if (stackList.get(i).getPassengerId().equals(pId)) {
                stackList.remove(i);
                return true;
            }
        }
        return false;
    }

    // UPDATE: Modify passenger details by ID
    public boolean update(String pId, String name, String address, String contact, String age, String gender) {
        for (PassengerItem item : stackList) {
            if (item.getPassengerId().equals(pId)) {
                item.setFullName(name);
                item.setAddress(address);
                item.setContact(contact);
                item.setAge(age);
                item.setGender(gender);
                return true;
            }
        }
        return false;
    }

    // READ: Get elements in LIFO order (Top to Bottom)
    public List<PassengerItem> getElements() {
        List<PassengerItem> reversed = new ArrayList<>();
        for (int i = stackList.size() - 1; i >= 0; i--) {
            reversed.add(stackList.get(i));
        }
        return reversed;
    }

    public boolean isEmpty() {
        return stackList.isEmpty();
    }

    public int size() {
        return stackList.size();
    }
}