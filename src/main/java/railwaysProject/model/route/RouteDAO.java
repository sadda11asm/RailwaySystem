package railwaysProject.model.route;


import java.util.List;

public interface RouteDAO {

    public List<Route> getRoutesFromTo(int from, int to, String date);

    List<CityRoute> getArrivalToTheCity(int cityId);

    List<CityRoute> getDepartureFromTheCity(int cityId);



}
