package railwaysProject.model.route;

import java.time.LocalDateTime;

public class Route {
    private int routeId;
    private String routeName;
    private String startDate;
    private int to;
    private int from;
    private String depDate;
    private String arrDate;


    public Route(int routeId, String routeName, String startDate, int to, int from, String depDate, String arrDate) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.startDate = startDate;
        this.to = to;
        this.from = from;
        this.depDate = depDate;
        this.arrDate = arrDate;
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

    public void setTo(int to) {
        this.to = to;
    }

    public void setFrom(int from) {
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

    public int getTo() {
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
                '}';
    }

    public int getFrom() {
        return from;
    }
}
