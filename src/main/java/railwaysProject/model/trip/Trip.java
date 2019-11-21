package railwaysProject.model.trip;

import java.util.Date;

public class Trip {
    private int ticketId; //
    private String fromStation;//
    private String toStation;//
    private Date fromDate;//
    private Date toDate;//
    private int trainId;//
    private int carriageNum;//
    private int seat;
    private int routeId;//
    private String routeName;
    private String firstName;


    private String lastName;


    private String documentID;

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getDocumentID() {
        return documentID;
    }

    public Trip(){
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getCarriageNum() {
        return carriageNum;
    }

    public void setCarriageNum(int carriageNum) {
        this.carriageNum = carriageNum;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public String getToStation() {
        return toStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getCarriage_num() {
        return carriageNum;
    }


    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }



    public Date getFrom_date() {
        return fromDate;
    }

    public Date getTo_date() {
        return toDate;
    }

    public void setTo_date(Date to_date) {
        this.toDate = toDate;
    }

    public String getFrom() {
        return fromStation;
    }

    public void setFrom(String from) {
        this.fromStation = from;
    }

    public String getTo() {
        return toStation;
    }

    public void setTo(String to) {
        this.toStation = to;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }
}
