package railwaysProject.view;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Preconditions;
import railwaysProject.controller.EmployeeController;
import railwaysProject.controller.RoutesController;
import railwaysProject.model.BookRequest;
import railwaysProject.model.BookResponse;
import railwaysProject.model.Employees.Adjustment;
import railwaysProject.model.Employees.Employee;
import railwaysProject.model.Passengers.Passenger;
import railwaysProject.model.Schedule.FinalSchedule;
import railwaysProject.model.Schedule.Schedule;
import railwaysProject.model.route.NewRoute;
import railwaysProject.model.route.Station;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.ArrayList;


@Path("/employees")
public class Employees {
    RoutesController routesController = ServiceLocator.getRoutesController();
    EmployeeController employeeController = ServiceLocator.getEmployeeController();
    @POST
    @Path("/newRoute")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewRoute(NewRoute route){
        int id = routesController.insertNewRoute(route);
        if(id == -1){
            return Response.serverError().build();
        }
        return Response.ok(new Gson().toJson(id)).build();
    }

    @POST
    @Path("/login")
    public Response login(@FormParam("email") String email, @FormParam("password") String password) {
        Employee employee = employeeController.getEmployeeByEmailAndPassword(email, password);
        if (employee == null) {
            return Response.status(401).build();
        }
        Gson gson = new Gson();
        return Response.ok(gson.toJson(employee)).build();
    }

    @DELETE
    @Path("/deleteTicket")
    public Response login(@FormParam("ticketId") int ticketId, @FormParam("trainId") int trainId,
                          @FormParam("routeId") int routeId){
        boolean isDeleted = employeeController.deleteTicket(ticketId, trainId, routeId);
        if(isDeleted){
            return Response.ok().build();
        }
        return Response.status(401).build();
    }


    @POST
    @Path("/createTicket")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTicket(BookRequest request) {
        System.out.println(request.getRoute_id()+request.getPass_id()+request.getEmail()+request.getDate());
        BookResponse res = routesController.bookTicket(request);
        if (!res.isSuccess()) return Response.status(403).build();
        return Response.ok(res).build();
    }


    @GET
    @Path("/schedule")
    public Response getSchedule(@QueryParam("e_id") int employee_id) {
        ArrayList<FinalSchedule> schedule = employeeController.getSchedule(employee_id);
        Gson gson = new Gson();
        return Response.ok(gson.toJson(schedule)).build();
    }

    @GET
    @Path("{e_id: [0-9]+}")
    public Response getEmployee(@PathParam("e_id") int e_id) {
        Employee schedule = employeeController.getEmployeeById(e_id);
        Gson gson = new Gson();
        return Response.ok(gson.toJson(schedule)).build();
    }


    @PUT
    @Path("/salary/{e_id: [0-9]+}")
    public Response updateSalary(@PathParam("e_id") int e_id, @QueryParam("salary") int salary) {
        Boolean updated = employeeController.updateSalary(e_id, salary);
        if (updated) {
            return Response.ok().build();
        }
        return Response.status(401).build();
    }

    @PUT
    @Path("/adjust/{e_id: [0-9]+}")
    public Response makeAdjustment(@PathParam("e_id") int e_id, Adjustment adjustment) {
        Boolean updated = employeeController.adjustHours(e_id, adjustment);
        if (updated) {
            return Response.ok().build();
        }
        return Response.status(401).build();
    }

    @DELETE
    @Path("/cancelRoute")
    public Response cancelRoute(@FormParam("routeId") int routeId, @FormParam("startDate") String startDate){
        System.out.println(5);
        if(employeeController.cancelRoute(routeId, startDate)) return Response.ok().build();
        return Response.status(304).build();
    }

    @GET
    @Path("/getStations")
    public Response getStations(){
        List<Station> listOfStations = employeeController.getStations();
        return listOfStations.size() > 0 ? Response.ok(new Gson().toJson(listOfStations)).build() : Response.status(204).build();
    }

    @GET
    @Path("/getEmployees")
    public Response getEmployees(@QueryParam("stationId") int stationId){
        List<Employee> employees = employeeController.getEmployees(stationId);
        return Response.ok(new Gson().toJson(employees)).build();
    }
}
