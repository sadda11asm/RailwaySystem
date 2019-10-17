package railwaysProject.model.Passengers;


public class Passenger {

    private int passengerId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;

    public Passenger(int passengerId, String firstName, String lastName, String email, String password, String phoneNumber) {
        this.passengerId =  passengerId;
        this.firstName =  firstName;
        this.lastName =  lastName;
        this.email =  email;
        this.password =  password;
        this.phoneNumber =  phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
