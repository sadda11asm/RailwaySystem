package railwaysProject.controller;

import railwaysProject.model.Employees.Adjustment;
import railwaysProject.model.Employees.Employee;
import railwaysProject.model.Employees.EmployeeDaoImpl;
import railwaysProject.model.Employees.ReqSchedule;
import railwaysProject.model.Schedule.FinalSchedule;
import railwaysProject.model.Schedule.Schedule;
import railwaysProject.model.Schedule.ScheduleDAO;
import railwaysProject.model.Schedule.ScheduleDaoImpl;

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
        for (ReqSchedule req: adjustment.getSchedule()) {
            int weekDay = req.getWeekDay();
            int startHour = req.getStartHour();
            int endHour = req.getEndHour();
            int numHours = findDuration(startHour, endHour);
            Schedule row = new Schedule(e_id, weekDay, startHour, numHours);
            schedule.put(weekDay, row);
        }
        return employeeDAO.adjustHours(schedule);

    }

    private int findDuration(int startHour, int endHour) {

        int dif = endHour-startHour;
        if (dif < 0) {
            dif+=24;
        }
        return dif;
    }
}
