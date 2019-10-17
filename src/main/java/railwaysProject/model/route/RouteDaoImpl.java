package railwaysProject.model.route;



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
                            "Arrival.station_id as to_station, Departure.date as dep_date, Departure.station_id as from_station " +
                            "FROM ((Route INNER JOIN Arrival ON Arrival.route_id = Route.route_id " +
                            "AND Route.start_date = Arrival.route_start_date) INNER JOIN Departure " +
                            "ON Departure.route_id = Route.route_id AND Departure.route_start_date = Route.start_date) " +
                            "WHERE Arrival.station_id = " + stationTo + " AND Departure.station_id = " + stationFrom + " AND start_date = " + date + " ;";
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
            ResultSet result = conn.createStatement().executeQuery(newSqlQuery);
            while(result.next()) {
                int routeId = result.getInt("route_id");
                String startDate = result.getString("start_date");
                String routeName = result.getString("route_name");
                int from = result.getInt("from_station");
                int to = result.getInt("to_station");
                String arrDate = result.getString("arr_date");
                String depDate = result.getString("dep_date");
//                System.out.println("routeId: " + routeId + ", route_name: " + routeName + "to: " + to);
                Route route = new Route(routeId, routeName, startDate, to, from, depDate, arrDate);
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
