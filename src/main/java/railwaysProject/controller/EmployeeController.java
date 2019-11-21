package railwaysProject.controller;

import railwaysProject.model.Employees.Adjustment;
import railwaysProject.model.Employees.Employee;
import railwaysProject.model.Employees.EmployeeDaoImpl;
import railwaysProject.model.route.Station;
import railwaysProject.util.ConnectionPool;
import railwaysProject.model.Employees.ReqSchedule;
import railwaysProject.model.Schedule.FinalSchedule;
import railwaysProject.model.Schedule.Schedule;
import railwaysProject.model.Schedule.ScheduleDAO;
import railwaysProject.model.Schedule.ScheduleDaoImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Array;
import java.util.*;

public class EmployeeController {
    private EmployeeDaoImpl employeeDAO;
    private ScheduleDAO scheduleDAO;

    public EmployeeController(EmployeeDaoImpl employeeDAO, ScheduleDAO scheduleDAO) {
        this.employeeDAO = employeeDAO;
        this.scheduleDAO = scheduleDAO;
    }

    public Employee getEmployeeByEmailAndPassword(String email, String password) {
        return employeeDAO.getEmployeeByEmailAndPassword(email, password);
    }

    public boolean deleteTicket(int ticketId,int trainId, int routeId){
        return employeeDAO.deleteTicket(ticketId,trainId, routeId);
    }


    public ArrayList<FinalSchedule> getSchedule(int e_id) {
        ArrayList<Schedule> rows =  scheduleDAO.getSchedule(e_id);


        Map<Integer, Schedule> weekDaySchedule = new HashMap<>();
        for (int i = 0 ; i < rows.size(); i++) {
            Schedule row = rows.get(i);
            weekDaySchedule.put(row.getWeek_day(), row);
        }

        // 1 - Monday, 7 - Sunday

        for (int i = 1; i <= 7; i++) {
            if (!weekDaySchedule.containsKey(i)) {
                Schedule row = weekDaySchedule.get(i);
                weekDaySchedule.put(i, new Schedule(e_id, i, 0, 0));
            }
        }

        ArrayList<FinalSchedule> finalSchedule = new ArrayList<>();

        for (Schedule row: weekDaySchedule.values()) {
            finalSchedule.add(row.toFinalSchedule(row));
        }

        return finalSchedule;
    }

    public Employee getEmployeeById(int e_id) {
        return employeeDAO.getEmployeeById(e_id);
    }

    public Boolean updateSalary(int e_id, int salary) {
        return employeeDAO.updateSalary(e_id, salary);
    }

    public Boolean adjustHours(int e_id, Adjustment adjustment) {
        Map<Integer, Schedule> schedule = new HashMap<>();
        ReqSchedule[] list = adjustment.getSchedule();
        for (int i = 0; i < list.length; i++) {
            ReqSchedule req = list[i];
            int weekDay = req.getWeekDay();
            int startHour = req.getStartHour();
            int endHour = req.getEndHour();
            int numHours = findDuration(startHour, endHour);
            Schedule row = new Schedule(e_id, weekDay, startHour, numHours);
            schedule.put(weekDay, row);
        }
        return employeeDAO.adjustHours(schedule, e_id);

    }

    private int findDuration(int startHour, int endHour) {

        int dif = endHour-startHour;
        if (dif < 0) {
            dif+=24;
        }
        return dif;
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


    public boolean isManager(Employee employee){
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            Statement statement = conn.createStatement();
            String query = "Select * from Manager where Employee_employee_id = " + employee.getEmployeeId() + ";";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isAgent(Employee employee){
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            Statement statement = conn.createStatement();
            String query = "Select * from Agent where Employee_employee_id = " + employee.getEmployeeId() + ";";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
