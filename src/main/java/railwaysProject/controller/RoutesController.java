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

import java.sql.*;
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


    private boolean coolTicket(String depDate, String arrDate, String start, String end) {
        System.out.println("cool ticket  " + depDate + " " + arrDate + " " + start + " " + end);
        if (depDate.compareTo(start)>=0 && depDate.compareTo(end)<0) return false;
        if (arrDate.compareTo(start)>0 && arrDate.compareTo(end)<=0) return false;
        if (depDate.compareTo(start)<=0 && arrDate.compareTo(end)>=0) return false;
        if (depDate.compareTo(start)>=0 && arrDate.compareTo(end)<=0) return false;
        return true;
    }
    public List<Seat> getSeatsInfo(String route_id,  String date, String depDate, String arrDate) {
        List<SeatEntity> seats = routeDAO.getAllSeats(route_id);
        List<TicketEntity> tickets = routeDAO.getBookedSeats(route_id, date);
//        System.out.println("tickets " + tickets.get(0).toString());
        Set<SeatEntity> booked = new HashSet<>();

        for (int i = 0; i < tickets.size(); i++) {
            TicketEntity ticket = tickets.get(i);
            if (!coolTicket(ticket.getDepDate(), ticket.getArrDate(), depDate, arrDate)) {
                System.out.println("Bad ticket: " + ticket.getSeatNum());
                SeatEntity seat = new SeatEntity(ticket.getSeatNum(), ticket.getRouteId(), ticket.getCarriageNum(), ticket.getTrainId());
                System.out.println(seat.toString());
                booked.add(seat);
            }
        }

        System.out.println(booked.size());

        List<Seat> ans = new ArrayList<>();
        for (int i = 0; i < seats.size(); i++) {
            SeatEntity seat = seats.get(i);
//            System.out.println(seat.toString());
            if (booked.contains(seat)) {
                ans.add(new Seat(seat.getSeatNum(), seat.getCarriageNum(), false));
            } else {
                ans.add(new Seat(seat.getSeatNum(), seat.getCarriageNum(), true));
            }
        }
        return ans;
    }

    public BookResponse bookTicket(BookRequest request) {
        Passenger pass = passengerDao.getUserByEmail(request.getEmail());
        if (pass==null) {
            int passId = passengerDao.signUpUser(request.getEmail(), request.getFirst_name(), request.getLast_name(), generatePassword());
            System.out.println("PASSID " + passId);
            request.setPass_id(passId);
        } else request.setPass_id(pass.getPassengerId());
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
            String query = "INSERT INTO Route(route_name) values ('"+ route.getRouteName()+"')";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next())routeId = rs.getInt(1);
            if(routeId != -1){
                insertNewRouteHelper(routeId, route);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return routeId;
    }

    private void insertNewRouteHelper(int routeId, NewRoute route){
        addTrain(routeId, route.getCarNum(), route.getSeatNum());
        insertRouteInstance(route, routeId);
        insertStations(routeId, route);
    }

    private void addTrain(int routeId, int carNum, int seatNum){
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            String query = "INSERT INTO Train(route_id) values (" + routeId + ")";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            int trainId = -1;
            if(rs.next()) trainId = rs.getInt(1);
            if(trainId != -1) addCarrSeat(routeId, trainId, carNum, seatNum);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void addCarrSeat(int routeId, int trainId, int carNum, int seatNum){
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            Statement statement = conn.createStatement();
            String car = "";
            String seat = "";
            for(int i = 1; i <= carNum; i++){
                car =   "INSERT INTO mydb.Carriage(train_id, route_id, carriage_num) values (" + trainId +
                        "," + routeId + "," + i + ")";
                statement.executeUpdate(car);
                for(int j = 1; j <= seatNum; j++){
                    seat =  "INSERT into mydb.Seat(train_id, route_id, carriage_num, seat_num) values(" +
                            trainId + "," + routeId + "," + i + "," + j + ")";
                    statement.executeUpdate(seat);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void insertRouteInstance(NewRoute route, int routeId){
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            Statement statement = conn.createStatement();
            String[] startDates = route.getDates();
            for(String startDate: startDates){
                String query = "INSERT INTO Route_Instance(start_date, route_id)" +
                        " values ('" + startDate + "'," + routeId+")";
                statement.executeUpdate(query);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private LocalDateTime incrementDate(LocalDateTime current, LocalTime duration){
        current = current.plusSeconds(duration.getSecond());
        current = current.plusMinutes(duration.getMinute());
        current = current.plusHours(duration.getHour());
        return current;
    }

    private LocalTime strToLocalTime(String time){
        int hour = Integer.parseInt(time.substring(0,2));
        int min = Integer.parseInt(time.substring(3,5));
        int sec = Integer.parseInt(time.substring(6));
        return LocalTime.of(hour, min, sec);
    }

    public LocalDate strToLocalDate(String date){
        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(5,7));
        int day = Integer.parseInt(date.substring(8));
        return LocalDate.of(year, month, day);

    }
    private void insertStations(int routeId, NewRoute route){
        String[] dates = route.getDates();
        StationDuration[] stations = route.getStations();
        Connection conn = ConnectionPool.getDatabaseConnection();
        try{
            Statement statement = conn.createStatement();
            for(int i = 0; i < dates.length; i ++){
                LocalDate date = strToLocalDate(dates[i]);
                LocalTime time = strToLocalTime(route.getStartTime());
                LocalDateTime current  = LocalDateTime.of(date, time);
                for(int j = 0; j < stations.length ; j++){
                    String depQuery = "";
                    String arQuery = "";
                    if(j == stations.length - 1){
                        depQuery = "Insert into Departure(station_id,route_id,route_start_date, date)" +
                                "values("+ stations[j].getStationId() + ","+
                                routeId + ",'" + dates[i] +"','"+ current + "')";
                        LocalTime duration = strToLocalTime(stations[j].getDuration());
                        current = incrementDate(current, duration);
                        arQuery = "INSERT INTO Arrival(station_id,route_id,route_start_date, date)" +
                                "values(" + route.getLastStation() +","+ routeId +",'"+
                                dates[i] +"','"+ current + "')";
                    }else {
                        depQuery = "Insert into Departure(station_id,route_id,route_start_date, date)" +
                                "values(" + stations[j].getStationId() + "," +
                                routeId + ",'" + dates[i] + "','" + current + "' )";
                        LocalTime duration = strToLocalTime(stations[j].getDuration());
                        current = incrementDate(current, duration);
                        arQuery = "INSERT INTO Arrival(station_id,route_id,route_start_date, date)" +
                                "values(" + stations[j + 1].getStationId() + "," + routeId + ",'" +
                                dates[i] + "','" + current + "')";
                    }
                    statement.executeUpdate(depQuery);
                    statement.executeUpdate(arQuery);
                }
            }
        }catch(SQLException  e){
            e.printStackTrace();
        }
    }
}


