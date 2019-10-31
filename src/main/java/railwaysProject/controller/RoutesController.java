package railwaysProject.controller;


import railwaysProject.model.BookRequest;
import railwaysProject.model.BookResponse;
import railwaysProject.model.Passengers.Passenger;
import railwaysProject.model.Passengers.PassengerDAO;
import railwaysProject.model.Passengers.PassengerDaoImpl;
import railwaysProject.model.TicketEntity;
import railwaysProject.model.seat.Seat;
import railwaysProject.model.route.CityRoute;
import railwaysProject.model.route.Route;
import railwaysProject.model.route.RouteDAO;
import railwaysProject.model.seat.SeatEntity;
import railwaysProject.model.route.*;

import railwaysProject.util.ConnectionPool;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.nio.charset.Charset;
import java.util.*;

public class RoutesController {

    RouteDAO routeDAO;
    PassengerDAO passengerDao;

    public RoutesController(RouteDAO routeDAO, PassengerDAO passengerDAO) {
        this.routeDAO = routeDAO;
        this.passengerDao = passengerDAO;
    }


    protected List<Route> getRoutes(int from, int to, String date) {
        return routeDAO.getRoutesFromTo(from, to, date);
    }

    public List<CityRoute> getScheduleOfCity(int cityId) {

        List<CityRoute> routes = routeDAO.getArrivalToTheCity(cityId);


        List<CityRoute> depRoutes = routeDAO.getDepartureFromTheCity(cityId);

//        for (CityRoute route: depRoutes) {
//            if (finalRoutes.containsKey(new Pair<>(route.getRouteId(), new Pair(route.getStartDate(), route.getStationId())))) {
//                CityRoute r = finalRoutes.get(new Pair<>(route.getRouteId(), new Pair(route.getStartDate(), route.getStationId())));
//                r.setDepDate(route.getDepDate());
//            } else {
//                finalRoutes.put(new Pair<>(route.getRouteId(), new Pair(route.getStartDate(), route.getStationId())), route);
//            }
//        }

        for (int i = 0; i < routes.size(); i++) {
            for (int j = 0; j < depRoutes.size(); j++) {
                if (depRoutes.get(j).getRouteId() == routes.get(i).getRouteId() &&
                    depRoutes.get(j).getDateStart() == routes.get(i).getDateStart() &&
                        depRoutes.get(j).getStationId() == routes.get(i).getStationId()) {
                    routes.get(i).setDepDate(depRoutes.get(j).getDepDate());
                }
            }
        }
        if (routes.size()==0) return depRoutes;
        return routes;
    }

    public List<Route> getRoutesFromTo(int from, int to, String dateTo) {
        return routeDAO.getRoutesFromTo(from, to, dateTo);
    }


    private boolean check(String depDate, String arrDate, String start, String end) {
        if (depDate.compareTo(start)>=0 && depDate.compareTo(end)<=0) return false;
        if (arrDate.compareTo(start)<=0 && arrDate.compareTo(end)>=0) return false;
        if (depDate.compareTo(start)<0 && arrDate.compareTo(end)>0) return false;
        return true;
    }
    public List<Seat> getSeatsInfo(String route_id,  String date, String depDate, String arrDate) {
        List<SeatEntity> seats = routeDAO.getAllSeats(route_id);
        List<TicketEntity> tickets = routeDAO.getBookedSeats(route_id, date);
        Set<SeatEntity> booked = new TreeSet<>();

        for (int i = 0; i < tickets.size(); i++) {
            TicketEntity ticket = tickets.get(i);
            if (!check(ticket.getDepDate(), ticket.getArrDate(), depDate, arrDate)) {
                booked.add(new SeatEntity(ticket.getSeatNum(), ticket.getRouteId(), ticket.getCarriageNum(), ticket.getTrainId()));
            }
        }

        List<Seat> ans = new ArrayList<>();
        for (int i = 0; i < seats.size(); i++) {
            SeatEntity seat = seats.get(i);
            if (booked.contains(seat)) {
                ans.add(new Seat(seat.getSeatNum(), seat.getCarriageNum(), false));
            } else {
                ans.add(new Seat(seat.getSeatNum(), seat.getCarriageNum(), true));
            }
        }
        return ans;
    }

    public BookResponse bookTicket(BookRequest request) {
        if (passengerDao.getUserByEmail(request.getEmail())==null) {
            int passId = passengerDao.signUpUser(request.getEmail(), request.getFirst_name(), request.getLast_name(), generatePassword());
            request.setPass_id(passId);
        }
        return routeDAO.bookTicket(request);
    }

    private String generatePassword() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        return generatedString;
    }

    public int insertNewRoute(NewRoute route){
        Connection conn = ConnectionPool.getDatabaseConnection();
        int routeId = -1;
        try{
            Statement statement = conn.createStatement();
            String query = "INSERT INTO Route(route_name) values ('"+ route.getRouteName()+"')";
            statement.execute(query);
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()){
                routeId = rs.getInt("route_id");
            }
            if(routeId != -1){
                addTrain(routeId, route.getCarNum(), route.getSeatNum());
                insertRouteInstance(route, routeId);
                insertStations(routeId, route);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return routeId;
    }
    private LocalDateTime incrementDate(LocalDateTime current, LocalTime duration){
        current = current.plusSeconds(duration.getSecond());
        current = current.plusMinutes(duration.getMinute());
        current = current.plusHours(duration.getHour());
        return current;

    }
    private void insertStations(int routeId, NewRoute route){
        LocalDate[] dates = route.getDates();
        StationDuration[] stations = route.getStations();
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            Statement statement = conn.createStatement();
            for(int i = 0; i < dates.length; i ++){
                LocalDateTime current  = LocalDateTime.of(dates[i], route.getStartTime());
                for(int j = 0; j < stations.length; j++){
                    String depQuery = "";
                    String arQuery = "";
                    if(j == stations.length - 1){
                        depQuery = "Insert into Departure(station_id,route_id,route_start_date, date)" +
                                "values("+ stations[j].getStationId() + ","+
                                routeId + "," + dates[i] +","+ current + " )";
                        current = incrementDate(current, stations[j].getDuration());
                        arQuery = "INSERT INTO Arrival(station_id,route_id,route_start_date, date)" +
                                "values(" + route.getLastStation() +","+ routeId +","+
                                dates[i] +","+ current + ")";
                    }
                    else {
                        depQuery = "Insert into Departure(station_id,route_id,route_start_date, date)" +
                                "values(" + stations[j].getStationId() + "," +
                                routeId + "," + dates[i] + "," + current + " )";
                        current = incrementDate(current, stations[j].getDuration());
                        arQuery = "INSERT INTO Arrival(station_id,route_id,route_start_date, date)" +
                                "values(" + stations[j + 1].getStationId() + "," + routeId + "," +
                                dates[i] + "," + current + ")";
                    }
                    statement.executeQuery(depQuery);
                    new Object().wait(1000);
                    statement.executeQuery(arQuery);
                }
            }
        }catch(SQLException | InterruptedException e){
            e.printStackTrace();
        }
    }
    private void insertRouteInstance(NewRoute route, int routeId){
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            Statement statement = conn.createStatement();
            LocalDate[] startDates = route.getDates();
            for(LocalDate startDate: startDates){
                String query = "INSERT INTO Route_Instance(start_date, route_id)" +
                        " values (" + startDate + "," + routeId+")";
                statement.execute(query);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void addTrain(int routeId, int carNum, int seatNum){
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            Statement statement = conn.createStatement();
            String query = "INSERT INTO Train(route_id) values (" + routeId + ")";
            statement.execute(query);
            ResultSet rs = statement.getGeneratedKeys();
            int trainId = -1;
            if(rs.next()) trainId = rs.getInt("train_id");
            if(trainId != -1) addCarrSeat(routeId, trainId, carNum, seatNum);

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void addCarrSeat(int routeId, int trainId, int carNum, int seatNum){
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            Statement statement = conn.createStatement();
            String car = "";
            String seat = "";
            for(int i = 1; i <= carNum; i++){
                car = "INSERT INTO Carriage(train_id, route_id, carriage_num) values (" + trainId +
                        "," + routeId + "," + i + ")";
                statement.executeQuery(car);
                for(int j = 1; j <= seatNum; j++){
                    seat = "INSERT into Seat(train_id, route_id, carriage_num, seat_num) values(" +
                            trainId + "," + routeId + "," + i + "," + j + ")";
                    statement.executeQuery(seat);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}


