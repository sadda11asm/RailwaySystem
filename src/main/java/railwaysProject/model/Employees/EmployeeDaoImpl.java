package railwaysProject.model.Employees;

import railwaysProject.controller.RoutesController;
import railwaysProject.util.ConnectionPool;
import railwaysProject.view.ServiceLocator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl {
    RoutesController routesController = ServiceLocator.getRoutesController();
    public Employee getEmployeeByEmailAndPassword(String email, String password) {
        List<Employee> employees = new ArrayList<>();
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String query = "Select * from Employee where email = '" + email + "' and password = '" + password + "'";
            ResultSet lists = myStatement.executeQuery(query);

            while(lists.next()){
                employees.add(new Employee(
                        lists.getInt("employee_id"),
                        lists.getInt ("salary"),
                        lists.getInt("Station_station_id"),
                        lists.getString("email"),
                        lists.getString("password")
                ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return employees.size() > 0 ? employees.get(0) : null;
    }

    public boolean deleteTicket(int ticketId,int trainId, int routeId){
        boolean ticketExists = false;
        boolean ticketDeleted = false;
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String query = "Select * from Ticket where ticket_id = " + ticketId + " and train_id = " + trainId +
                    " and route_id = " + routeId + " and route_start_date > curdate();";
            ResultSet ticket = myStatement.executeQuery(query);

            if(ticket.next()){
                ticketExists = true;
            }
            if(!ticketExists) return false;
            query = "Delete from Ticket where ticket_id = " + ticketId + " and train_id = " + trainId +
                    " and route_id = " + routeId;
            myStatement.executeUpdate(query);
            ticketDeleted = true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ticketDeleted;
    }
    public void notifyAboutDelete(int routeId, LocalDate startDate){
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{

        }
    }
    public boolean cancelRoute(int routeId, String startDate){
        boolean isCanceled = false;
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            Statement statement = conn.createStatement();
            LocalDate date = routesController.strToLocalDate(startDate);
            String query = "Select * from Route_Instance where start_date = " + date
                         + " and route_id = " + routeId + ";";
            ResultSet rs = statement.executeQuery(query);
            if(!rs.next()) return false;
            notifyAboutDelete(routeId,date);
            String routeInstance = "DELETE FROM Route_Instance where start_date = " + date
                    + " and route_id = " + routeId + ";";
            String arrivals = "DELETE FROM  Arrival where route_start_date = " + date
                    + " and route_id = " + routeId + ";";
            String departures = "DELETE FROM  Departure where route_start_date = " + date
                    + " and route_id = " + routeId + ";";
            statement.executeUpdate(departures);
            statement.executeUpdate(arrivals);
            statement.executeUpdate(routeInstance);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isCanceled;
    }

}
