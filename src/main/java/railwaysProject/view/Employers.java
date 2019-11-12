package railwaysProject.view;

import railwaysProject.controller.RoutesController;
import railwaysProject.model.route.NewRoute;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/employers")
public class Employers {
    RoutesController routesController = ServiceLocator.getRoutesController();

    @POST
    @Path("/newRoute")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewRoute(NewRoute route){
        System.out.println(route);
        int id = routesController.insertNewRoute(route);
        if(id == -1){
            return Response.serverError().build();
        }
        return Response.ok().build();
    }


}
