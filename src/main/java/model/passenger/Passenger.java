package model.passenger;

import org.codehaus.jackson.annotate.JsonProperty;

public class Passenger {

    private int passId;
    private String firstName;
    private String lastName;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @JsonProperty(value = "first_name")
    public String getFirstName() {
        return firstName;
    }
    @JsonProperty(value = "last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty(value = "pass_id")
    public int getPassId() {
        return passId;
    }

    public void setPassId(int passId) {
        this.passId = passId;
    }

}
