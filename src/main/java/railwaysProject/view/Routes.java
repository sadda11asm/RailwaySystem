package railwaysProject.view;


import com.google.gson.Gson;
import railwaysProject.controller.RoutesController;
import railwaysProject.model.route.Route;
import railwaysProject.model.route.RouteDAO;
import railwaysProject.model.route.RouteDaoImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
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
}
