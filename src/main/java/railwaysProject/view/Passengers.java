package railwaysProject.view;

import com.google.gson.Gson;
import railwaysProject.model.Passengers.Passenger;
import railwaysProject.controller.PassengerController;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/passengers")
public class Passengers {
    private PassengerController passengerController = ServiceLocator.getPassengerController();

    @POST
    @Path("/login")
    public Response login(@FormParam("email") String email, @FormParam("password") String password) {
        System.out.println(email + password);
        Passenger passenger = passengerController.getUserByEmailAndPassword(email, password);
        if (passenger == null) {
            return Response.status(403).build();
        }
        Gson gson = new Gson();
        return Response.ok(gson.toJson(passenger)).build();
    }

    @POST
    @Path("/sign_up")
    public Response signUp(@FormParam("email") String email, @FormParam("firstName") String firstName,
                           @FormParam("lastName") String lastName, @FormParam("password") String password) {

        return passengerController.signUpUser(email, firstName, lastName, password);
    }

    @GET
    @Path("/test")
    public Response test() {
        return Response.status(403).entity("Some message").build();
    }

    @POST
    @Path("/test")
    public Response postTest() {
        return Response.status(403).entity("Some message").build();
    }

    @GET
    public Response getAllUsers() {
        Gson gson = new Gson();
        return Response.ok(passengerController.getAllUsers()).build();
    }

}
