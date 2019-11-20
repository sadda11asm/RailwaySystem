package railwaysProject.model.Employees;

public class Employee {
    private int employeeId;
    private int salary;
    private int stationId;
    private String email;
    private String password;
    private boolean isManager = false;
    private boolean isAgent = false;


    public Employee(int employeeId, int salary, int stationId, String email, String password) {
        this.employeeId =  employeeId;
        this.salary =  salary;
        this.stationId =  stationId;
        this.email =  email;
        this.password =  password;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setAgent(boolean agent) {
        isAgent = agent;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public boolean isManager() {
        return isManager;
    }
}
