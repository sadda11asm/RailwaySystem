package railwaysProject.view;

import com.google.gson.Gson;
import railwaysProject.controller.RoutesController;
import railwaysProject.model.route.newRoute;

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
    public Response createNewRoute(newRoute route){
        int id = routesController.insertNewRoute(route);
        if(id == -1){
            return Response.serverError().build();
        }
        return Response.ok().build();
    }


}
