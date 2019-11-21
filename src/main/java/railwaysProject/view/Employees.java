package railwaysProject.view;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Preconditions;
import railwaysProject.controller.EmployeeController;
import railwaysProject.controller.RoutesController;
import railwaysProject.model.BookRequest;
import railwaysProject.model.BookResponse;
import railwaysProject.model.Employees.Adjustment;
import railwaysProject.model.Employees.Employee;
import railwaysProject.model.LoggerInfo;
import railwaysProject.model.Passengers.Passenger;
import railwaysProject.model.Schedule.FinalSchedule;
import railwaysProject.model.Schedule.Schedule;
import railwaysProject.model.route.NewRoute;
import railwaysProject.model.route.Station;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;


@Path("/employees")
public class Employees {
    RoutesController routesController = ServiceLocator.getRoutesController();
    EmployeeController employeeController = ServiceLocator.getEmployeeController();
    LoggerInfo loggerInfo = ServiceLocator.getLoggerInfo();

    @POST
    @Path("/newRoute")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewRoute(NewRoute route, @Context Request request){
        addLogRouteCreation(request);
        int id = routesController.insertNewRoute(route);
        if(id == -1){
            return Response.serverError().build();
        }
        return Response.ok(new Gson().toJson(id)).build();
    }

    @OPTIONS
    @Path("/newRoute")
    public Response createNewRoute(){
        return Response.ok().build();
    }

    @POST
    @Path("/login")
    public Response login(@FormParam("email") String email, @FormParam("password") String password, @Context Request request) {
        Employee employee = employeeController.getEmployeeByEmailAndPassword(email, password);
        addLoginLog(request, employee, email);
        if (employee == null) {
            return Response.status(401).build();
        }
        Gson gson = new Gson();
        if(employeeController.isAgent(employee)) employee.setAgent(true);
        if(employeeController.isManager(employee)) employee.setManager(true);
        return Response.ok(gson.toJson(employee)).build();
    }

    @OPTIONS
    @Path("/login")
    public Response login(){
        return Response.ok().build();
    }

    @DELETE
    @Path("/deleteTicket")
    public Response deleteTicket(@FormParam("ticketId") int ticketId, @FormParam("trainId") int trainId,
                          @FormParam("routeId") int routeId, @Context Request request){
        boolean isDeleted = employeeController.deleteTicket(ticketId, trainId, routeId);
        deleteTicketLog(request, isDeleted, ticketId);
        if(isDeleted){
            return Response.ok().build();
        }
        return Response.status(401).build();
    }

    @OPTIONS
    @Path("/deleteTicket")
    public Response deleteTicket(){
        return Response.ok().build();
    }

    @POST
    @Path("/createTicket")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTicket(BookRequest request, @Context Request req) {
        System.out.println(request.getRoute_id()+request.getPass_id()+request.getEmail()+request.getDate());
        BookResponse res = routesController.bookTicket(request);
        createTicketLog(res, request, req);
        if (!res.isSuccess()) return Response.status(403).build();
        return Response.ok(res).build();
    }

    @OPTIONS
    @Path("/createTicket")
    public Response createTicket(){
        return Response.ok().build();
    }

    @GET
    @Path("/schedule")
    public Response getSchedule(@QueryParam("e_id") int employee_id, @Context Request request) {
        ArrayList<FinalSchedule> schedule = employeeController.getSchedule(employee_id);
        getScheduleLog(request, employeeController.getEmployeeById(employee_id));
        Gson gson = new Gson();
        return Response.ok(gson.toJson(schedule)).build();
    }

    @GET
    @Path("{e_id: [0-9]+}")
    public Response getEmployee(@PathParam("e_id") int e_id, @Context Request request) {
        Employee schedule = employeeController.getEmployeeById(e_id);
        getEmployeeLog(request, schedule);
        Gson gson = new Gson();
        return Response.ok(gson.toJson(schedule)).build();
    }



    @PUT
    @Path("/salary/{e_id: [0-9]+}")
    public Response updateSalary(@PathParam("e_id") int e_id, @QueryParam("salary") int salary, @Context Request request) {
        Boolean updated = employeeController.updateSalary(e_id, salary);
        updateSalaryLog(request, updated, employeeController.getEmployeeById(e_id), salary);
        if (updated) {
            return Response.ok().build();
        }
        return Response.status(401).build();
    }

    @OPTIONS
    @Path("/salary/{e_id: [0-9]+}")
    public Response updateSalary(){
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/adjust/{e_id: [0-9]+}")
    public Response makeAdjustment(@PathParam("e_id") int e_id, Adjustment adjustment, @Context Request request) {
        Boolean updated = employeeController.adjustHours(e_id, adjustment);
        makePayRollLog(request, updated, employeeController.getEmployeeById(e_id));
        if (updated) {
            return Response.ok().build();
        }
        return Response.status(401).build();
    }


    @OPTIONS
    @Path("/adjust/{e_id: [0-9]+}")
    public Response makeAdjustment(){
        return Response.ok().build();
    }

    @DELETE
    @Path("/cancelRoute")
    public Response cancelRoute(@FormParam("routeId") int routeId, @FormParam("startDate") String startDate, @Context Request request){
        if(employeeController.cancelRoute(routeId, startDate)) return Response.ok().build();
        deleteLogRouteCreation(request);
        return Response.status(304).build();
    }

    @OPTIONS
    @Path("/cancelRoute")
    public Response cancelRoute(){
        return Response.ok().build();
    }

    @GET
    @Path("/getStations")
    public Response getStations(@Context Request request){
        List<Station> listOfStations = employeeController.getStations();
        return listOfStations.size() > 0 ? Response.ok(new Gson().toJson(listOfStations)).build() : Response.status(204).build();
    }

    @GET
    @Path("/getEmployees")
    public Response getEmployees(@QueryParam("stationId") int stationId, @Context Request request){
        List<Employee> employees = employeeController.getEmployees(stationId);
        return Response.ok(new Gson().toJson(employees)).build();
    }

    private void addLogRouteCreation(Request request) {
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Manager 1";
            String action = "Create Route request";
            String addInfo = "";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }

    private void deleteLogRouteCreation(Request request) {
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Manager 1";
            String action = "Delete Route request";
            String addInfo = "";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }

    private void addLoginLog(Request request, Employee employee, String email) {
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = email;
            String action = "Create Route request";
            String addInfo = employee!=null ? " successfully logged in" : "incorrect username or password";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }

    private void deleteTicketLog(Request request, boolean isDeleted, int ticketId) {
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Agent 1";
            String action = "Delete Ticket request";
            String addInfo = isDeleted ? " Successfully deleted ticket " + ticketId : "Not successful deletion of the ticket " + ticketId;
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }

    private void createTicketLog(BookResponse res, BookRequest req, Request request) {
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Agent";
            String action = "Create ticket from " + req.getFrom() + " to " + req.getTo();
            String addInfo = res.isSuccess() ? " Successfully created ticket with id " + res.getTicket_id() : "Not successful creation of the ticket ";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }

    private void getScheduleLog(Request request, Employee employee) {
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Manager 1";
            String action = "Get Schedule of the employee " + employee.getEmail() ;
            String addInfo = "";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }

    private void getEmployeeLog(Request request, Employee employee) {
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Manager 1";
            String action = "Get Info about the employee " + employee.getEmail() ;
            String addInfo = "";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }

    private void updateSalaryLog(Request request, Boolean updated, Employee employee, int salary) {
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Manager 1";
            String action = "Update salary of the employee " + employee.getEmail() + " to " + " salary " + salary ;
            String addInfo = updated ? " Successfully updated" : " Not a successfull update";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }

    private void makePayRollLog(Request request, Boolean updated, Employee employee) {
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Manager 1";
            String action = "Update schedule of the employee " + employee.getEmail();
            String addInfo = updated ? " Successfully updated" : " Not a successfull update";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }

    @POST
    @Path("/switchMode")
    public Response switchModeOfLog(@QueryParam("isOn") boolean isOn){
        System.out.println("Before switching the mode:" + ServiceLocator.isLoggingOn());
        ServiceLocator.setLoggingOn(isOn);
        System.out.println("After switching the mode:" + ServiceLocator.isLoggingOn());
        return Response.ok().build();
    }


}
