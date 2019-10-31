package railwaysProject.view;

import com.google.gson.Gson;
import railwaysProject.controller.RoutesController;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/employers")
public class Employers {
    RoutesController routesController = ServiceLocator.getRoutesController();

    @POST
    @Path("/newRoute")
    public Response createNewRoute(@FormParam("routeName") String routeName, @FormParam("carNum") int carNum,
                                   @FormParam("seatNum") int seatNum, @FormParam("startDate") Date startDate){
        int routeId = routesController.crateNewRoute(routeName, carNum, seatNum, startDate);
        return Response.ok(new Gson().toJson(routeId)).build();
    }


}
