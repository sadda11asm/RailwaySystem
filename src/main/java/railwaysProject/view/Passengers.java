package railwaysProject.view;

import com.google.gson.Gson;
import railwaysProject.model.LoggerInfo;
import railwaysProject.model.Passengers.Passenger;
import railwaysProject.controller.PassengerController;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Date;

@Path("/passengers")
public class Passengers {
    private PassengerController passengerController = ServiceLocator.getPassengerController();
    private LoggerInfo loggerInfo = ServiceLocator.getLoggerInfo();

    @POST
    @Path("/login")
    public Response login(@Context Request request, @FormParam("email") String email, @FormParam("password") String password) {
        addLogLogin(request);
        Passenger passenger = passengerController.getUserByEmailAndPassword(email, password);
        if (passenger == null) {
            return Response.status(403).build();
        }
        Gson gson = new Gson();
        return Response.ok(gson.toJson(passenger)).build();
    }

    private void addLogLogin(Request request){
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Unknown user";
            String action = "Passenger is logging in";
            String addInfo = "";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }

    @OPTIONS
    @Path("/login")
    public Response login(){
        return Response.ok().build();
    }

    @POST
    @Path("/sign_up")
    public Response signUp(@Context Request request, @FormParam("email") String email, @FormParam("firstName") String firstName,
                           @FormParam("lastName") String lastName, @FormParam("password") String password) {
        addLogSignUp(request);
        System.out.println("receiving email = " + email + ", firstName = " + firstName + ", lastName = " + lastName + ", password = " + password);
        return passengerController.signUpUser(email, firstName, lastName, password);
    }

    private void addLogSignUp(Request request){
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Unknown user";
            String action = "Passenger is signing up";
            String addInfo = "";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
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
    public Response getAllUsers(@Context Request request) {
        addLogGetAllUsers(request);
        Gson gson = new Gson();
        return Response.ok(passengerController.getAllUsers()).build();
    }

    private void addLogGetAllUsers(Request request){
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Unknown user";
            String action = "All users are retrieved.";
            String addInfo = "";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }

    @GET
    @Path("/profileInfo")
    public Response getProfileInfo(@Context Request request,@QueryParam("passengerId") int id){
        addLogProfileInfo(request);
        return Response.ok(new Gson().toJson(passengerController.getPassengerInfo(id))).build();
    }

    private void addLogProfileInfo(Request request){
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Unknown user";
            String action = "Getting the information about passenger";
            String addInfo = "";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }

    @GET
    @Path("/pastTrip")  //past trips
    public Response getPastTrip(@Context Request request, @QueryParam("passengerId") int id){
        addLogPastTrip(request);
        return Response.ok(new Gson().toJson(passengerController.getPastTrip(id))).build();
    }

    private void addLogPastTrip(Request request){
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Unknown user";
            String action = "Getting information about past trip";
            String addInfo = "";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }

    @GET
    @Path("/nextTrip")  //next trips
    public Response getNextTrip(@Context Request request, @QueryParam("passengerId") int id){
        addLogNextTrip(request);
        return Response.ok(new Gson().toJson(passengerController.getNextTrip(id))).build();
    }

    private void addLogNextTrip(Request request){
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Unknown user";
            String action = "Getting information about next trip";
            String addInfo = "";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }
}
