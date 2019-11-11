package railwaysProject.model;


public class BookRequest {

    private int route_id;
    private String date;
    private int to;
    private int from;
    private String email;
    private String phone;
    private String first_name;
    private String last_name;
    private int carriage_num;
    private int train_id;
    private int seat_num;
    private int pass_id;

    public BookRequest( int route_id,
             String date,
             int to,
             int from,
             String email,
             String phone,
             String first_name,
             String last_name,
             int carriage_num,
             int train_id,
             int seat_num) {
        this.date = date;
        this.to = to;
        this.route_id = route_id;
        this.first_name = first_name;
        this.from = from;
        this.email = email;
        this.last_name = last_name;
        this.carriage_num = carriage_num;
        this.train_id = train_id;
        this.seat_num = seat_num;
        this.phone = phone;
    }

    public int getRoute_id() {
        return route_id;
    }

    public String getDate() {
        return date;
    }

    public int getTo() {
        return to;
    }

    public int getFrom() {
        return from;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
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

    public int getTrain_id() {
        return train_id;
    }

    public int getSeat_num() {
        return seat_num;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public void setTrain_id(int train_id) {
        this.train_id = train_id;
    }

    public void setSeat_num(int seat_num) {
        this.seat_num = seat_num;
    }

    public int getPass_id() {
        return pass_id;
    }

    public void setPass_id(int pass_id) {
        this.pass_id = pass_id;
    }
}
