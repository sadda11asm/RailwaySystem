package railwaysProject.view;

import railwaysProject.controller.EmployeeController;
import railwaysProject.controller.PassengerController;
import railwaysProject.controller.RoutesController;
import railwaysProject.controller.CityController;
import railwaysProject.model.Employees.EmployeeDaoImpl;
import railwaysProject.model.Passengers.Passenger;
import railwaysProject.model.Passengers.PassengerDAO;
import railwaysProject.model.Passengers.PassengerDaoImpl;
import railwaysProject.model.route.RouteDAO;
import railwaysProject.model.route.RouteDaoImpl;

public class ServiceLocator {

    private static CityController cityController;
    private static RoutesController routesController;
    private static PassengerController passengerController;
    private static RouteDAO routeDAO;
    private static PassengerDAO passengerDAO;
    private static EmployeeDaoImpl employeeDAO;
    private static EmployeeController employeeController;

    public static RouteDAO getRouteDAO() {
        if (routeDAO == null) {
            routeDAO = new RouteDaoImpl();
        }
        return routeDAO;
    }

    public static EmployeeDaoImpl getEmployeeDAO() {
        if (employeeDAO == null) {
            employeeDAO = new EmployeeDaoImpl();
        }
        return employeeDAO;
    }

    public static EmployeeController getEmployeeController(){
        if(employeeController == null){
            employeeController = new EmployeeController(getEmployeeDAO());
        }
        return employeeController;
    }

    public static CityController getCityController() {
        if (cityController == null) {
            cityController = new CityController();
        }
        return cityController;
    }

    public static RoutesController getRoutesController() {
        if (routesController == null) {
            routesController = new RoutesController(getRouteDAO(), getPassengerDAO());
        }
        return routesController;
    }

    public static PassengerController getPassengerController() {
        if (passengerController == null) {
            passengerController = new PassengerController(getPassengerDAO());
        }
        return passengerController;
    }

    public static PassengerDAO getPassengerDAO() {
        if (passengerDAO == null) {
            passengerDAO = new PassengerDaoImpl();
        }
        return passengerDAO;
    }
}
