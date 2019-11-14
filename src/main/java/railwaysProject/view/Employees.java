package railwaysProject.view;

import com.google.gson.Gson;
import railwaysProject.controller.EmployeeController;
import railwaysProject.controller.RoutesController;
import railwaysProject.model.Employees.Employee;
import railwaysProject.model.Passengers.Passenger;
import railwaysProject.model.route.NewRoute;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    @DELETE
    @Path("/cancelRoute")
    public Response cancelRoute(@FormParam("routeId") int routeId, @FormParam("startDate") String startDate){
        if(employeeController.cancelRoute(routeId, startDate)) return Response.ok().build();
        return Response.status(304).build();
    }


}
