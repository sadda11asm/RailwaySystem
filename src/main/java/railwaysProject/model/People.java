package railwaysProject.model;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class People {

    @JsonbProperty("first_name") private String first_name;
    @JsonbProperty("last_name") private String last_name;
    @JsonbProperty("carriage_num") private int carriage_num;
    @JsonbProperty("seat_num") private int seat_num;
    @JsonbProperty("documentID") private String documentID;

    @JsonbCreator()
    public People( @JsonbProperty("first_name") String first_name,
                   @JsonbProperty("last_name") String last_name,
                   @JsonbProperty("carriage_num") int carriage_num,
                   @JsonbProperty("seat_num") int seat_num,
                   @JsonbProperty("documentID") String documentID) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.carriage_num = carriage_num;
        this.seat_num = seat_num;
        this.documentID = documentID;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public int getCarriage_num() {
        return carriage_num;
    }

    public int getSeat_num() {
        return seat_num;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setCarriage_num(int carriage_num) {
        this.carriage_num = carriage_num;
    }

    public void setSeat_num(int seat_num) {
        this.seat_num = seat_num;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    @Override
    public String toString() {
        return "People{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", carriage_num=" + carriage_num +
                ", seat_num=" + seat_num +
                ", documentID='" + documentID + '\'' +
                '}';
    }
}
