package railwaysProject.view;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

import railwaysProject.controller.EmailService;
import railwaysProject.controller.RoutesController;
import railwaysProject.model.Cities.City;
import railwaysProject.controller.CityController;
import com.google.gson.Gson;
import railwaysProject.model.LoggerInfo;
import railwaysProject.model.route.CityRoute;

@Path("/cities")
public class Cities {
    private CityController cityController = ServiceLocator.getCityController();
    private RoutesController routesController = ServiceLocator.getRoutesController();
    private LoggerInfo loggerInfo = ServiceLocator.getLoggerInfo();
    private EmailService emailService = ServiceLocator.getEmailService();


    @GET
    public Response getListOfCities(@Context Request request){
        addLogCitiesList(request);
        List<City> listOfCities = cityController.getCities();
        String cities = new Gson().toJson(listOfCities);
        return Response.ok(cities).build();
    }

    @GET
    @Path("{cityId: [0-9]+}")
    public Response getScheduleOfCity(@PathParam("cityId") int cityId, @Context Request request) {
        addLogScheduleByCity(request, cityId);
        List<CityRoute> routes = routesController.getScheduleOfCity(cityId);
        String routesResponse = new Gson().toJson(routes);
        return Response.ok(routesResponse).build();
    }

    private void addLogScheduleByCity(Request request, int city) {
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Unknown user";
            String action = "Get schedule for the city with id " + city;
            String addInfo = "";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }

    private void addLogCitiesList(Request request) {
        if (ServiceLocator.isLoggingOn()) {
            String requestType = request.getMethod();
            String whoIs = "Unknown user";
            String action = "Get list of cities";
            String addInfo = "";
            LocalDateTime time = LocalDateTime.now();
            ApiRequestInfo log = new ApiRequestInfo(requestType, time, addInfo, whoIs, action);
            loggerInfo.addLogs(log);
        }
    }

}

