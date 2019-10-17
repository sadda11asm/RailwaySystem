package railwaysProject.view;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;
import railwaysProject.Controller.City;
import railwaysProject.Controller.Controller;
import com.google.gson.Gson;

@Path("/cities")
public class Cities {
    private Controller controller = new Controller();

    @GET
    public Response getListOfCities(){
        List<City> listOfCities = controller.getCities();
        String cities = new Gson().toJson(listOfCities);
        return Response.ok(cities).build();
    }

}

