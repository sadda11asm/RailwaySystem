package railwaysProject.model.Employees;

import railwaysProject.model.Schedule.Schedule;
import railwaysProject.util.ConnectionPool;
import railwaysProject.view.ServiceLocator;
import railwaysProject.controller.RoutesController;


/*import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Map;


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

    public Employee getEmployeeById(int e_id) {
        List<Employee> employees = new ArrayList<>();
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String query = "Select * from Employee where employee_id = " + e_id + ";";
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

    public Boolean updateSalary(int e_id, int salary) {
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String query = "UPDATE Employee SET salary = " + salary + " WHERE employee_id = " + e_id + ";";
            myStatement.executeUpdate(query);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Boolean adjustHours(Map<Integer, Schedule> schedule, int e_id) {
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            for (int weekDay: schedule.keySet()) {
                Schedule row = schedule.get(weekDay);
                String querySelect = "SELECT * FROM SCHEDULE WHERE week_day = " + weekDay + ";";
                String queryUpdate = "UPDATE SCHEDULE SET start_hour = " + row.getStart_hour() + ", hours_num = " + row.getHours_num() + " WHERE week_day = " + weekDay + ";";
                String queryInsert = "INSERT INTO SCHEDULE (e_id, week_day, start_hour, hours_num) VALUES(" + e_id + "," + weekDay + ", " + row.getStart_hour() + "," + row.getHours_num() + ");";
                ResultSet rs = myStatement.executeQuery(querySelect);
                if(!rs.next()) {
                    myStatement.executeUpdate(queryInsert);
                } else {
                    myStatement.executeUpdate(queryUpdate);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void notifyAboutDeleteRoute(int routeId, LocalDate startDate){
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            Statement statement = conn.createStatement();
//            String getEmails = "Select P.email as email"
//                             + "from Passenger as P,"
//                             + "Ticket as T where P.passenger_id = T.Passenger_passenger_id "
//                             + " and T.route_start_date = " + startDate
//                             + " and T.route_id = " + routeId + ";";
//            List<String> emails = new ArrayList<>();
//            ResultSet rs = statement.executeQuery(getEmails);
//            while(rs.next()){
//                emails.add(rs.getString("email"));
//            }
            String deleteTicket = "Delete from Ticket where route_start_date = '" + startDate
                    + "' and route_id = " + routeId + ";";
            statement.executeUpdate(deleteTicket);

            //sendDeleteRouteAdvisory(emails, routeId, startDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean cancelRoute(int routeId, String startDate){
        boolean isCanceled = false;
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            Statement statement = conn.createStatement();
            LocalDate date = routesController.strToLocalDate(startDate);
            String query = "Select * from Route_Instance where start_date = '" + date
                    + "' and start_date > curdate() and route_id = " + routeId + ";";
            ResultSet rs = statement.executeQuery(query);
            if(!rs.next()) return false;
            notifyAboutDeleteRoute(routeId,date);
            String routeInstance = "DELETE FROM Route_Instance where start_date = '" + date
                    + "' and route_id = " + routeId + ";";
            String arrivals = "DELETE FROM  Arrival where route_start_date = '" + date
                    + "' and route_id = " + routeId + ";";
            String departures = "DELETE FROM  Departure where route_start_date = '" + date
                    + "' and route_id = " + routeId + ";";
            statement.executeUpdate(departures);
            statement.executeUpdate(arrivals);
            statement.executeUpdate(routeInstance);
            isCanceled = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isCanceled;
    }
}
