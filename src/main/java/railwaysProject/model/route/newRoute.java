package railwaysProject.model.route;

import java.util.Date;

public class newRoute {
    private int routeId;
    private Date startDate;
    private String routeName;

    public newRoute(int routeId, Date startDate, String routeName){
        this.routeId = routeId;
        this.startDate = startDate;
        this.routeName = routeName;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }
}
