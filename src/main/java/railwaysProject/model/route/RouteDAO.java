package railwaysProject.model.route;


import java.util.List;

public interface RouteDAO {

    public List<Route> getRoutesFromTo(int from, int to, String date);

    List<CityRoute> geArrivalToTheCity(int cityId);

    List<CityRoute> geDepartureFromTheCity(int cityId);



}
