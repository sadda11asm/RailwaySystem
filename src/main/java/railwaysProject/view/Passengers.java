package railwaysProject.view;

import com.google.gson.Gson;
import railwaysProject.model.Passengers.Passenger;
import railwaysProject.controller.PassengerController;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;

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

    @OPTIONS
    @Path("/login")
    public Response login(){
        return Response.ok().build();
    }

    @POST
    @Path("/sign_up")
    public Response signUp(@FormParam("email") String email, @FormParam("firstName") String firstName,
                           @FormParam("lastName") String lastName, @FormParam("password") String password) {
        System.out.println("receiving email = " + email + ", firstName = " + firstName + ", lastName = " + lastName + ", password = " + password);
        return passengerController.signUpUser(email, firstName, lastName, password);
    }

    @OPTIONS
    @Path("/sign_up")
    public Response signUp(){
        return Response.ok().build();
    }

    @GET
    @Path("/test")
    public Response test() {

        System.out.println("TESTING");
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

    @GET
    @Path("/profileInfo")
    public Response getProfileInfo(@QueryParam("passengerId") int id){

        return Response.ok(new Gson().toJson(passengerController.getPassengerInfo(id))).build();
    }

    @GET
    @Path("/pastTrip")  //past trips
    public Response getPastTrip(@QueryParam("passengerId") int id){
        return Response.ok(new Gson().toJson(passengerController.getPastTrip(id))).build();
    }

    @GET
    @Path("/nextTrip")  //next trips
    public Response getNextTrip(@QueryParam("passengerId") int id){
        return Response.ok(new Gson().toJson(passengerController.getNextTrip(id))).build();
    }
}
