package railwaysProject.controller;

import railwaysProject.model.Employees.Employee;
import railwaysProject.model.Employees.EmployeeDaoImpl;
import railwaysProject.model.route.Station;
import railwaysProject.util.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {
    EmployeeDaoImpl employeeDAO;

    public EmployeeController(EmployeeDaoImpl employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public Employee getEmployeeByEmailAndPassword(String email, String password) {
        return employeeDAO.getEmployeeByEmailAndPassword(email, password);
    }

    public boolean deleteTicket(int ticketId,int trainId, int routeId){
        return employeeDAO.deleteTicket(ticketId,trainId, routeId);
    }

    public boolean cancelRoute(int routeId, String startDate){
        return employeeDAO.cancelRoute(routeId, startDate);
    }

    public List<Station> getStations(){
        List<Station> stations = new ArrayList<>();
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            Statement statement = conn.createStatement();
            String query = "Select station_id," +
                    "station_name from Station order by station_name asc ; ";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                stations.add(new Station(rs.getInt("station_id"), rs.getString("station_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stations;
    }

    public List<Employee> getEmployees(int stationId){
        List<Employee> employees = new ArrayList<>();
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            Statement statement = conn.createStatement();
            String query = "Select * from Employee" +
                    " where Station_station_id = " + stationId +
                    ";" ;
            ResultSet lists = statement.executeQuery(query);
            while(lists.next()){
                employees.add(new Employee(
                        lists.getInt("employee_id"),
                        lists.getInt ("salary"),
                        lists.getInt("Station_station_id"),
                        lists.getString("email"),
                        lists.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
