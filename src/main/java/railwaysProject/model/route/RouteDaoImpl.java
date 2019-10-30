package railwaysProject.model.route;



import railwaysProject.model.Cities.City;
import railwaysProject.util.ConnectionPool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDAO {


    @Override
    public List<Route> getRoutesFromTo(int stationFrom, int stationTo, String date) {


        String sqlQuery =   "SELECT Route.route_id, Route.start_date, Route.route_name, Arrival.date as arr_date, " +
                            "Arrival.station_id as to_station, Departure.date as dep_date, Departure.station_id as from_station, " +
                            "stto.station_name as to_name, stfrom.station_name as from_name " +
                            "FROM ((Route INNER JOIN Arrival ON Arrival.route_id = Route.route_id " +
                            "AND Route.start_date = Arrival.route_start_date) INNER JOIN Departure " +
                            "ON Departure.route_id = Route.route_id AND Departure.route_start_date = Route.start_date), station stto, " +
                            "station stfrom\n" +
                            "WHERE start_date = " + date + " AND stto.station_id = Arrival.station_id AND stfrom.station_id = Departure.station_id " +
                            "AND Arrival.station_id IN (SELECT station_id FROM station WHERE locality_locality_id = " + stationTo + ") " +
                            "AND Departure.station_id IN (SELECT station_id FROM station WHERE locality_locality_id = " + stationFrom +");";

        String newSqlQuery =    "SELECT Route.route_id, Route.start_date, Route.route_name, Arrival.date as arr_date, " +
                                "Arrival.station_id as to_station, Departure.date as dep_date, Departure.station_id as from_station " +
                                "FROM ((Route INNER JOIN Arrival ON Arrival.route_id = Route.route_id " +
                                "AND Route.start_date = Arrival.route_start_date) INNER JOIN Departure " +
                                "ON Departure.route_id = Route.route_id AND Departure.route_start_date = Route.start_date) " +
                                "WHERE start_date = " + date + " " +
                                "AND Arrival.station_id IN (SELECT station_id FROM station WHERE locality_locality_id = " + stationTo + ") " +
                                "AND Departure.station_id IN (SELECT station_id FROM station WHERE locality_locality_id = " + stationFrom +");";

        Connection conn = null;
        ArrayList<Route> routes = new ArrayList<>();
        try {
            conn = ConnectionPool.getDatabaseConnection();
            ResultSet result = conn.createStatement().executeQuery(sqlQuery);
            while(result.next()) {
                int routeId = result.getInt("route_id");
                String startDate = result.getString("start_date");
                String routeName = result.getString("route_name");
                int from = result.getInt("from_station");
                int to = result.getInt("to_station");
                String toName = result.getString("to_name");
                String fromName = result.getString("from_name");
                String arrDate = result.getString("arr_date");
                String depDate = result.getString("dep_date");
//                System.out.println("routeId: " + routeId + ", route_name: " + routeName + "to: " + to);
                Route route = new Route(routeId, routeName, startDate, new Station(to, toName), new Station(from, fromName), depDate, arrDate);
                System.out.println(route.toString());
                routes.add(route);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();


        return routes;
    }


    @Override
    public List<CityRoute> getArrivalToTheCity(int cityId) {

        String newSqlQuery =    "SELECT " +
                                "Route.route_id, Route.start_date, Route.route_name, Arrival.station_id, Arrival.date as arr_date, station.station_name " +
                                "FROM ((Route INNER JOIN Arrival ON Arrival.route_id = Route.route_id " +
                                "AND Route.start_date = Arrival.route_start_date) INNER JOIN station on station.station_id = arrival.station_id) " +
                                "WHERE Arrival.station_id in (SELECT station_id FROM station WHERE locality_locality_id = " + cityId + ");";

        Connection conn;
        ArrayList<CityRoute> routes = new ArrayList<>();
        try {
            conn = ConnectionPool.getDatabaseConnection();
            ResultSet result = conn.createStatement().executeQuery(newSqlQuery);
            while(result.next()) {
                int routeId = result.getInt("route_id");
                String startDate = result.getString("start_date");
                String routeName = result.getString("route_name");
                int stationId = result.getInt("station_id");
                String stationName = result.getString("station_name");
                String arrDate = result.getString("arr_date");
//                System.out.println("routeId: " + routeId + ", route_name: " + routeName + "to: " + to);
                CityRoute route = new CityRoute(routeId, routeName, startDate, stationId, stationName, null, arrDate);
                System.out.println(route.toString());
                routes.add(route);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();


        return routes;
    }

    @Override
    public List<CityRoute> getDepartureFromTheCity(int cityId) {
        String newSqlQuery =    "SELECT " +
                "Route.route_id, Route.start_date, Route.route_name, Departure.station_id, Departure.date as dep_date, station.station_name " +
                "FROM ((Route INNER JOIN Departure ON Departure.route_id = Route.route_id " +
                "AND Route.start_date = Departure.route_start_date) INNER JOIN station on station.station_id = Departure.station_id) " +
                "WHERE Departure.station_id in (SELECT station_id FROM station WHERE locality_locality_id = " + cityId + ");";

        Connection conn;
        ArrayList<CityRoute> routes = new ArrayList<>();
        try {
            conn = ConnectionPool.getDatabaseConnection();
            ResultSet result = conn.createStatement().executeQuery(newSqlQuery);
            while(result.next()) {
                int routeId = result.getInt("route_id");
                String startDate = result.getString("start_date");
                String routeName = result.getString("route_name");
                int stationId = result.getInt("station_id");
                String stationName = result.getString("station_name");
                String depDate = result.getString("dep_date");
//                System.out.println("routeId: " + routeId + ", route_name: " + routeName + "to: " + to);
                CityRoute route = new CityRoute(routeId, routeName, startDate, stationId, stationName, depDate, null);
                System.out.println(route.toString());
                routes.add(route);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
        return routes;
    }
}
