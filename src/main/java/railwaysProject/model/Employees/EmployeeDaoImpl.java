package railwaysProject.model.Employees;

import railwaysProject.util.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl {
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
}
