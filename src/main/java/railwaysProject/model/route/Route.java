package railwaysProject.model.route;

import java.time.LocalDateTime;

public class Route {
    private int routeId;
    private String routeName;
    private String startDate;
    private Station to;
    private Station from;
    private String depDate;
    private String arrDate;
    private String toName;
    private String fromName;
    private int trainId;

    public Route(int routeId, int trainId, String routeName, String startDate, Station to, Station from, String depDate, String arrDate) {
        this.routeId = routeId;
        this.trainId = trainId;
        this.startDate = startDate;
        this.routeName = routeName;
        this.to = to;
        this.from = from;
        this.depDate = depDate;
        this.arrDate = arrDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getDepDate() {
        return depDate;
    }

    public String getArrDate() {
        return arrDate;
    }

    public String getToName() {
        return toName;
    }

    public String getFromName() {
        return fromName;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public void setArrDate(String arrDate) {
        this.arrDate = arrDate;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setDateStart(String dateStart) {
        this.startDate = dateStart;
    }

    public void setTo(Station to) {
        this.to = to;
    }

    public void setFrom(Station from) {
        this.from = from;
    }

    public int getRouteId() {
        return routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getDateStart() {
        return startDate;
    }

    public Station getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", routeName='" + routeName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", to=" + to +
                ", from=" + from +
                ", depDate='" + depDate + '\'' +
                ", arrDate='" + arrDate + '\'' +
                ", trainId='" + trainId + '\'' +
                '}';
    }

    public Station getFrom() {
        return from;
    }


}


