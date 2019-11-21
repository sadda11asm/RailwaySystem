package railwaysProject.model.route;



import railwaysProject.model.BookRequest;
import railwaysProject.model.BookResponse;
import railwaysProject.model.TicketEntity;
import railwaysProject.model.seat.Seat;
import railwaysProject.model.seat.SeatEntity;
import railwaysProject.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDAO {


    @Override
    public List<Route> getRoutesFromTo(int stationFrom, int stationTo, String date) {


        String sqlQuery =   "SELECT Route.route_id, Route.start_date, R.route_name, Arrival.date as arr_date, " +
                            "Arrival.station_id as to_station, Departure.date as dep_date, Departure.station_id as from_station, " +
                            "stto.station_name as to_name, stfrom.station_name as from_name, " +
                            "Train.train_id "+
                            "FROM Train,  " +
                            "((Route_Instance as Route INNER JOIN Arrival ON Arrival.route_id = Route.route_id " +
                            "AND Route.start_date = Arrival.route_start_date) INNER JOIN Departure " +
                            "ON Departure.route_id = Route.route_id AND Departure.route_start_date = Route.start_date), Route R, Station stto, " +
                            "Station stfrom\n" +
                            "WHERE R.route_id = Route.route_id AND start_date = " + date + " AND stto.station_id = Arrival.station_id AND stfrom.station_id = Departure.station_id " +
                            "AND Arrival.station_id IN (SELECT station_id FROM Station WHERE locality_locality_id = " + stationTo + ") " +
                            "AND Departure.station_id IN (SELECT station_id FROM Station WHERE locality_locality_id = " + stationFrom +") " +
                            "and Train.route_id = Route.route_id;";

        String newSqlQuery =    "SELECT Route.route_id, Route.start_date, Arrival.date as arr_date, " +
                                "Arrival.station_id as to_station, Departure.date as dep_date, Departure.station_id as from_station " +
                                "FROM ((Route_Instance as Route INNER JOIN Arrival ON Arrival.route_id = Route.route_id " +
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
                int trainId = result.getInt("train_id");
//                System.out.println("routeId: " + routeId + ", route_name: " + routeName + "to: " + to);
                Route route = new Route(routeId, trainId, routeName, startDate, new Station(to, toName), new Station(from, fromName), depDate, arrDate);
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
                                "Route.route_id, Route.start_date, R.route_name,  Arrival.station_id, Arrival.date as arr_date, station.station_name " +
                                "FROM ((Route_Instance Route INNER JOIN Arrival ON Arrival.route_id = Route.route_id " +
                                "AND Route.start_date = Arrival.route_start_date) INNER JOIN station on station.station_id = arrival.station_id), Route R " +
                                "WHERE R.route_id = Route.route_id AND Arrival.station_id in (SELECT station_id FROM station WHERE locality_locality_id = " + cityId + ");";

        Connection conn;
        ArrayList<CityRoute> routes = new ArrayList<>();
        try {
            conn = ConnectionPool.getDatabaseConnection();
            ResultSet result = conn.createStatement().executeQuery(newSqlQuery);
            while(result.next()) {
                int routeId = result.getInt("route_id");
                String startDate = result.getString("start_date");
                int stationId = result.getInt("station_id");
                String routeName = result.getString("route_name");
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
                "Route.route_id, Route.start_date, R.route_name, Departure.station_id, Departure.date as dep_date, station.station_name " +
                "FROM ((Route_Instance Route INNER JOIN Departure ON Departure.route_id = Route.route_id " +
                "AND Route.start_date = Departure.route_start_date) INNER JOIN station on station.station_id = Departure.station_id), Route R " +
                "WHERE R.route_id = Route.route_id AND Departure.station_id in (SELECT station_id FROM station WHERE locality_locality_id = " + cityId + ");";

        Connection conn;
        ArrayList<CityRoute> routes = new ArrayList<>();
        try {
            conn = ConnectionPool.getDatabaseConnection();
             ResultSet result = conn.createStatement().executeQuery(newSqlQuery);
            while(result.next()) {
                int routeId = result.getInt("route_id");
                String routeName = result.getString("route_name");
                String startDate = result.getString("start_date");
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

    @Override
    public List<SeatEntity> getAllSeats(String route_id) {
        String query = "SELECT * FROM SEAT WHERE route_id = " + route_id + ";";
        Connection conn;
        ArrayList<SeatEntity> seats = new ArrayList<>();
        try {
            conn = ConnectionPool.getDatabaseConnection();
            ResultSet result = conn.createStatement().executeQuery(query);
            while(result.next()) {
                int routeId = result.getInt("route_id");
                int carriageNum = result.getInt("carriage_num");
                int seatNum = result.getInt("seat_num");
                int trainId = result.getInt("train_id");
//                System.out.println("routeId: " + routeId + ", route_name: " + routeName + "to: " + to);
                SeatEntity seat = new SeatEntity(seatNum, routeId, carriageNum, trainId);
                seats.add(seat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return seats;
    }

    @Override
    public List<TicketEntity> getBookedSeats(String routeId, String date) {
        String query = "SELECT t.ticket_id, t.train_id, t.carriage_num, t.seat_num, a.date as arr_date, d.date as dep_date\n" +
                "FROM Ticket t\n" +
                "left outer join Arrival a on t.station_to = a.station_id AND t.route_id = a.route_id AND t.route_start_date = a.route_start_date\n" +
                "left outer join Departure d on t.station_from = d.station_id AND t.route_id = d.route_id AND t.route_start_date = d.route_start_date\n" +
                "WHERE t.route_id = " + routeId + " AND t.route_start_date = '" + date +"';";
        Connection conn;
        ArrayList<TicketEntity> tickets = new ArrayList<>();
        try {
            conn = ConnectionPool.getDatabaseConnection();
            ResultSet result = conn.createStatement().executeQuery(query);
            while(result.next()) {
                int ticketId = result.getInt("ticket_id");
                int carriageNum = result.getInt("carriage_num");
                int seatNum = result.getInt("seat_num");
                int trainId = result.getInt("train_id");
                String arrDate = result.getString("arr_date");
                String depDate = result.getString("dep_date");
//                System.out.println("routeId: " + routeId + ", route_name: " + routeName + "to: " + to);
                TicketEntity ticket = new TicketEntity(seatNum, Integer.valueOf(routeId), carriageNum, trainId, date, ticketId, arrDate, depDate);
                tickets.add(ticket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tickets;
    }

    @Override
    public List<Route> getRoutes(){
        String query = "Select * from Route;";
        ArrayList<Route> routes = new ArrayList<>();
        try{
            Connection conn = ConnectionPool.getDatabaseConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                routes.add(new Route(rs.getInt("route_id"),
                        0,
                        rs.getString("route_name"),
                        null,
                        null,
                        null,
                        null,
                        null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }

    @Override
    public BookResponse bookTicket(BookRequest request) {

        System.out.println(request.toString());
        System.out.println("INSERT into Ticket (train_id, route_id, route_start_date, carriage_num, seat_num, station_from, station_to, Passenger_passenger_id) VALUES (" +
                request.getTrain_id() +
                "," +
                request.getRoute_id() +
                ",'" +
                request.getDate() +
                "'," +
                request.getCarriage_num() +
                "," +
                request.getSeat_num() +
                "," +
                request.getFrom() +
                "," +
                request.getTo() +
                "," +
                request.getPass_id() +
                ");");

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = ConnectionPool.getDatabaseConnection()
                    .prepareStatement
                            ("INSERT into Ticket (train_id, route_id, route_start_date, carriage_num, seat_num, station_from, station_to, Passenger_passenger_id) VALUES (" +
                                    request.getTrain_id() +
                                    "," +
                                    request.getRoute_id() +
                                    ",'" +
                                    request.getDate() +
                                    "'," +
                                    request.getCarriage_num() +
                                    "," +
                                    request.getSeat_num() +
                                    "," +
                                    request.getFrom() +
                                    "," +
                                    request.getTo() +
                                    "," +
                                    request.getPass_id() +
                                    ");", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            ResultSet tableKeys = preparedStatement.getGeneratedKeys();
            int ticket_id = -1;
            while(tableKeys.next()) {
                ticket_id = (int) tableKeys.getLong(1);
                return new BookResponse(true, ticket_id);
            }
            return new BookResponse(false, ticket_id);
        } catch (SQLException e) {
            e.printStackTrace();
            return new BookResponse(false, -1);
        }
    }
}
