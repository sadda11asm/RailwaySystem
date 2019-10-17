package railwaysProject.controller;


import railwaysProject.model.route.Route;
import railwaysProject.model.route.RouteDAO;

import java.util.List;

public class RoutesController {

    RouteDAO routeDAO;

    public RoutesController(RouteDAO routeDAO) {
        this.routeDAO = routeDAO;
    }


    protected List<Route> getRoutes(int from, int to, String date) {
        return routeDAO.getRoutesFromTo(from, to, date);
//        List<Route>
    }
}
