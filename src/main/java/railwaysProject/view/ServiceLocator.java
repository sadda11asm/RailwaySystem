package railwaysProject.view;

import railwaysProject.controller.*;
import railwaysProject.model.Employees.EmployeeDaoImpl;
import railwaysProject.model.LoggerInfo;
import railwaysProject.model.Passengers.Passenger;
import railwaysProject.model.Passengers.PassengerDAO;
import railwaysProject.model.Passengers.PassengerDaoImpl;
import railwaysProject.model.Schedule.ScheduleDAO;
import railwaysProject.model.Schedule.ScheduleDaoImpl;
import railwaysProject.model.route.RouteDAO;
import railwaysProject.model.route.RouteDaoImpl;

public class ServiceLocator {

    private static CityController cityController;
    private static RoutesController routesController;
    private static PassengerController passengerController;
    private static RouteDAO routeDAO;
    private static ScheduleDAO scheduleDAO;
    private static PassengerDAO passengerDAO;
    private static EmployeeDaoImpl employeeDAO;
    private static EmployeeController employeeController;
    private static EmailService emailService;
    private static LoggerInfo loggerInfo;
    private static boolean loggingOn = true;


    public static boolean isLoggingOn() {
        return loggingOn;
    }
    public static LoggerInfo getLoggerInfo() {
        if (loggerInfo == null) {
            loggerInfo = new LoggerInfo();
        }
        return loggerInfo;
    }

    public static EmailService getEmailService() {
        if (emailService == null) {
            emailService = new EmailService();
        }
        return emailService;
    }

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


    public static ScheduleDAO getScheduleDAO() {
        if (scheduleDAO == null) {
            scheduleDAO = new ScheduleDaoImpl();
        }
        return scheduleDAO;
    }
    public static EmployeeController getEmployeeController(){
        if(employeeController == null){
            employeeController = new EmployeeController(getEmployeeDAO(), getScheduleDAO());
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
