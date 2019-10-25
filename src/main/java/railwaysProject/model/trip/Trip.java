package railwaysProject.model.trip;

import java.util.Date;

public class Trip {
    private String ticketId;
    private String from;
    private String to;
    private Date from_date;
    private Date to_date;



    public Trip(String ticketId, String from, String to, Date from_date, Date to_date){
        this.ticketId = ticketId;
        this.from = from;
        this.to = to;
        this.from_date = from_date;
        this.to_date = to_date;
    }

    public Date getFrom_date() {
        return from_date;
    }

    public Date getTo_date() {
        return to_date;
    }

    public void setTo_date(Date to_date) {
        this.to_date = to_date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom_date(Date from_date) {
        this.from_date = from_date;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
}
