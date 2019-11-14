package railwaysProject.controller;

import railwaysProject.model.Employees.Employee;
import railwaysProject.model.Employees.EmployeeDaoImpl;

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
}
