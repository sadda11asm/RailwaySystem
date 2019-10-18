package railwaysProject.controller;


import railwaysProject.model.Cities.City;
import railwaysProject.model.route.CityRoute;
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

    public List<CityRoute> getScheduleOfCity(int cityId) {

        List<CityRoute> routes = routeDAO.geArrivalToTheCity(cityId);

        List<CityRoute> depRoutes = routeDAO.geDepartureFromTheCity(cityId);

        for (int i = 0; i < routes.size(); i++) {
            for (int j = 0; j < depRoutes.size(); j++) {
                if (depRoutes.get(j).getRouteId() == routes.get(i).getRouteId() &&
                    depRoutes.get(j).getDateStart() == routes.get(i).getDateStart() &&
                        depRoutes.get(j).getStationId() == routes.get(i).getStationId()) {
                    routes.get(i).setDepDate(depRoutes.get(j).getDepDate());
                }
            }
        }
        if (routes.size()==0) return depRoutes;
        return routes;
    }

}
