package railwaysProject.model;

public class TicketEntity {

    private int seatNum;
    private int routeId;
    private int carriageNum;
    private int trainId;
    private String startDate;
    private int ticketId;
    private String arrDate;
    private String depDate;

    public TicketEntity(int seatNum,int routeId, int carriageNum, int trainId, String date, int ticketId, String arrDate, String depDate) {
        this.seatNum = seatNum;
        this.routeId = routeId;
        this.carriageNum = carriageNum;
        this.trainId = trainId;
        this.arrDate = date;
        this.ticketId = ticketId;
        this.arrDate = arrDate;
        this.depDate = depDate;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public int getRouteId() {
        return routeId;
    }

    public int getCarriageNum() {
        return carriageNum;
    }

    public int getTrainId() {
        return trainId;
    }

    public String getStartDate() {
        return startDate;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getArrDate() {
        return arrDate;
    }

    public String getDepDate() {
        return depDate;
    }


    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public void setCarriageNum(int carriageNum) {
        this.carriageNum = carriageNum;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public void setArrDate(String arrDate) {
        this.arrDate = arrDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    @Override
    public String toString() {
        return "TicketEntity{" +
                "seatNum=" + seatNum +
                ", routeId=" + routeId +
                ", carriageNum=" + carriageNum +
                ", trainId=" + trainId +
                ", startDate='" + startDate + '\'' +
                ", ticketId=" + ticketId +
                ", arrDate='" + arrDate + '\'' +
                ", depDate='" + depDate + '\'' +
                '}';
    }


    //     TicketEntity(seatNum, Integer.valueOf(routeId), carriageNum, trainId, String date, ticketId, arrDate, depDate);
}
