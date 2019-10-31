package railwaysProject.view;


import com.google.gson.Gson;
import railwaysProject.controller.RoutesController;
import railwaysProject.model.BookRequest;
import railwaysProject.model.BookResponse;
import railwaysProject.model.seat.Seat;
import railwaysProject.model.route.Route;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/routes")
public class Routes {

    RoutesController controller = ServiceLocator.getRoutesController();
    @GET
    public Response getRoutesByPreference(@QueryParam("from") int from, @QueryParam("to")int to, @QueryParam("date_to") String dateTo, @QueryParam("data_back") String dateFrom) {
        System.out.println("DEBUG from: " + from + ", to: " + to + "date: " + dateTo);
        List<Route> routes =  controller.getRoutesFromTo(from, to, dateTo);
        String routesResponse = new Gson().toJson(routes);

        return Response.ok(routesResponse).build();
    }

    @GET
    @Path("/{routeId:[0-9]+}/seats")
    public Response getSeatsOfARoute(@PathParam("routeId") String routeId, @QueryParam("date") String date, @QueryParam("dep_date") String depDate,@QueryParam("arr_date") String arrDate) {
//        System.out.println(route_id+date+depDate+arrDate);
        List<Seat> res = controller.getSeatsInfo(routeId, date, depDate, arrDate);
        String response = new Gson().toJson(res);
        return Response.ok(response).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/book")
    public Response bookTicket(BookRequest request) {
//        System.out.println(route_id+date+depDate+arrDate);
        BookResponse res = controller.bookTicket(request);
        if (!res.isSuccess()) return Response.status(403).build();
        return Response.ok(res).build();
    }
}
