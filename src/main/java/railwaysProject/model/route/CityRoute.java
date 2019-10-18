package railwaysProject.model.route;

public class CityRoute {
    private int routeId;
    private String routeName;
    private String startDate;
    private int stationId;
    private String stationName;
    private String depDate;
    private String arrDate;

    public CityRoute(int routeId, String routeName, String startDate, int stationId, String stationName, String depDate, String arrDate) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.startDate = startDate;
        this.stationId = stationId;
        this.stationName = stationName;
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

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public void setArrDate(String arrDate) {
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

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
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

    public int getStationId() {
        return stationId;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", routeName='" + routeName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", to=" + stationId +
                ", from=" + stationName +
                ", depDate='" + depDate + '\'' +
                ", arrDate='" + arrDate + '\'' +
                '}';
    }

    public String getStationName() {
        return stationName;
    }
}
