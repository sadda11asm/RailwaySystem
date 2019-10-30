package railwaysProject.model.trip;

import java.util.Date;

public class Trip {
    private String ticketId;
    private String fromStation;
    private String toStation;
    private Date fromDate;
    private Date toDate;
    private int trainId;
    private int carriageNum;
    private int seat;
    private int routeId;

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

    public void setCarriage_num(int carriageNum) {
        this.carriageNum = carriageNum;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public Trip(){
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

    public void setFrom_date(Date from_date) {
        this.fromDate = from_date;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
}
