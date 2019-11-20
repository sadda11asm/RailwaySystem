package railwaysProject.view;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

import railwaysProject.controller.EmailService;
import railwaysProject.controller.RoutesController;
import railwaysProject.model.Cities.City;
import railwaysProject.controller.CityController;
import com.google.gson.Gson;
import railwaysProject.model.route.CityRoute;

@Path("/cities")
public class Cities {
    private CityController cityController = ServiceLocator.getCityController();
    private RoutesController routesController = ServiceLocator.getRoutesController();
    private EmailService emailService = ServiceLocator.getEmailService();


    @GET
    public Response getListOfCities(){
        emailService.sendAgentCreated("saddam.asmatullayev@nu.edu.kz");
        List<City> listOfCities = cityController.getCities();
        String cities = new Gson().toJson(listOfCities);
        return Response.ok(cities).build();
    }

    @GET
    @Path("{cityId: [0-9]+}")
    public Response getScheduleOfCity(@PathParam("cityId") int cityId) {
        List<CityRoute> routes = routesController.getScheduleOfCity(cityId);
        String routesResponse = new Gson().toJson(routes);
        return Response.ok(routesResponse).build();
    }

}

