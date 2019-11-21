package railwaysProject.view;


import com.google.gson.Gson;
import railwaysProject.controller.RoutesController;
import railwaysProject.model.BookRequest;
import railwaysProject.model.BookResponse;
import railwaysProject.model.LoggerInfo;
import railwaysProject.model.seat.Seat;
import railwaysProject.model.route.Route;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.LocalDateTime;
import java.util.List;


@Path("/routes")
public class Routes {

    RoutesController controller = ServiceLocator.getRoutesController();
    private LoggerInfo loggerInfo = ServiceLocator.getLoggerInfo();


    @GET
    public Response getRoutesByPreference(@Context Request request, @QueryParam("from") int from, @QueryParam("to")int to, @QueryParam("date_to") String dateTo, @QueryParam("data_back") String dateFrom) {
        System.out.println("DEBUG from: " + from + ", to: " + to + "date: " + dateTo);
        List<Route> routes =  controller.getRoutesFromTo(from, to, dateTo);
        String routesResponse = new Gson().toJson(routes);
        addLogGetRoutesByPreference(request);
        return Response.ok(routesResponse).build();
    }


    private void addLogGetRoutesByPreference(Request request) {
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Unknown user";
            String action = "Getting routes by preference.";
            String addInfo = "";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }


    @GET
    @Path("/{routeId:[0-9]+}/seats")
    public Response getSeatsOfARoute(@Context Request request, @PathParam("routeId") String routeId, @QueryParam("date") String date, @QueryParam("dep_date") String depDate,@QueryParam("arr_date") String arrDate) {
        addLogGetSeatsOfARoute(request);
        System.out.println(routeId+date+depDate+arrDate);
        List<Seat> res = controller.getSeatsInfo(routeId, date, depDate, arrDate);
        String response = new Gson().toJson(res);
        return Response.ok(response).build();
    }

    private void addLogGetSeatsOfARoute(Request request) {
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Unknown user";
            String action = "Getting seats of a route.";
            String addInfo = "";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }


    @POST
    @Path("/book")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response bookTicket(@Context Request requestt, BookRequest request) {
        addLogBookTicket(requestt);
        System.out.println(request.getRoute_id()+request.getPass_id()+request.getEmail()+request.getDate());
        BookResponse res = controller.bookTicket(request);
        if (!res.isSuccess()) return Response.status(403).build();
        return Response.ok(res).build();
    }

    private void addLogBookTicket(Request request) {
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Unknown user";
            String action = "Booking a Ticket.";
            String addInfo = "";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }


    @OPTIONS
    @Path("/book")
    public Response login(){
        return Response.ok().build();
    }



    @GET
    @Path("/getRoutes")
    public Response getRoutes(){
        return Response.ok(new Gson().toJson(controller.getRoutes())).build();
    }
}
