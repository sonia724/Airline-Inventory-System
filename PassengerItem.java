package airlineinventory;

public class PassengerItem {
    private String passengerId;
    private String fullName;
    private String address;
    private String contact;
    private String age;
    private String gender;

    public PassengerItem(String passengerId, String fullName, String address, String contact, String age, String gender) {
        this.passengerId = passengerId;
        this.fullName = fullName;
        this.address = address;
        this.contact = contact;
        this.age = age;
        this.gender = gender;
    }

    public String getPassengerId() { return passengerId; }
    public void setPassengerId(String passengerId) { this.passengerId = passengerId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}