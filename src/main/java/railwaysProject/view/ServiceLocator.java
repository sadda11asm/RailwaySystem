package railwaysProject.view;

import railwaysProject.controller.RoutesController;
import railwaysProject.controller.CityController;
import railwaysProject.model.route.RouteDAO;
import railwaysProject.model.route.RouteDaoImpl;

public class ServiceLocator {

    private static CityController cityController;
    private static RoutesController routesController;
    private static RouteDAO routeDAO;

    public static RouteDAO getRouteDAO() {
        if (routeDAO == null) {
            routeDAO = new RouteDaoImpl();
        }
        return routeDAO;
    }

    public static CityController getCityController() {
        if (cityController == null) {
            cityController = new CityController();
        }
        return cityController;
    }

    public static RoutesController getRoutesController() {
        if (routesController == null) {
            routesController = new RoutesController(getRouteDAO());
        }
        return routesController;
    }
}
