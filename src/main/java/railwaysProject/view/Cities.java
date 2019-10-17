package railwaysProject.view;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;
import railwaysProject.model.Cities.City;
import railwaysProject.model.Cities.CityController;
import com.google.gson.Gson;

@Path("/cities")
public class Cities {
    private CityController cityController = new CityController();

    @GET
    public Response getListOfCities(){
        List<City> listOfCities = cityController.getCities();
        String cities = new Gson().toJson(listOfCities);
        return Response.ok(cities).build();
    }

}

