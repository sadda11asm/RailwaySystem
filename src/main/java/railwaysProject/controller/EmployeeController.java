package railwaysProject.controller;

import railwaysProject.model.Employees.Employee;
import railwaysProject.model.Employees.EmployeeDaoImpl;
import railwaysProject.model.Schedule.FinalSchedule;
import railwaysProject.model.Schedule.Schedule;
import railwaysProject.model.Schedule.ScheduleDAO;
import railwaysProject.model.Schedule.ScheduleDaoImpl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

}
