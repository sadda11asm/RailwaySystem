package railwaysProject.controller;


import railwaysProject.model.Passengers.Passenger;
import railwaysProject.model.Passengers.PassengerDAO;
import railwaysProject.model.route.RouteDAO;
import railwaysProject.util.ConnectionPool;
import railwaysProject.model.trip.Trip;

import javax.swing.plaf.nimbus.State;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerController {

    PassengerDAO passengerDAO;

    public PassengerController(PassengerDAO passengerDAO) {
        this.passengerDAO = passengerDAO;
    }


    public Passenger getUserByEmailAndPassword(String email, String password) {
        return passengerDAO.getUserByEmailAndPassword(email, password);
    }

    public List<Passenger> getAllUsers() {
        return passengerDAO.getAllUsers();
    }

    public Passenger getUserByEmail(String email) {
       return passengerDAO.getUserByEmail(email);
    }

    public Response signUpUser(String email,
                               String firstName,
                               String lastName,
                               String password) {
        Passenger passenger = getUserByEmail(email);
        if (passenger != null) {
            return Response.status(403).entity("User with such an email already exists").build();
        }
        int passId = passengerDAO.signUpUser(email, firstName, lastName, password);
        if (passId == -1) {
            return Response.status(410).build();
        }
        return Response.ok(passId).build();
    }

    /*public boolean[] getTypeUser(int passengerId){
        Type typeOfPass = new Type(false, false);
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String man_query = "Select id from Manager where id =  " + passengerId + ";";
            ResultSet man_rs = myStatement.executeQuery(man_query);
            String agent_query = "Select id from Agent where id =  " + passengerId + ";";
            ResultSet ag_rs = myStatement.executeQuery(agent_query);
            while(man_rs.next()) response[0] = true;
            while(ag_rs.next()) response[1] = true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return response;
    }*/

    public  Passenger getPassengerInfo(int passengerId){
        Passenger passenger = new Passenger(passengerId, null, null,null,null,null);
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            System.out.println("Error");
            Statement myStatement = myConnection.createStatement();
            String infoQuery = "Select * from Passenger where passenger_id =  " + passengerId + ";";
            ResultSet rs = myStatement.executeQuery(infoQuery);
            while(rs.next()){
                passenger.setFirstName(rs.getString("first_name"));
                passenger.setLastName(rs.getString("last_name"));
                passenger.setEmail(rs.getString("email"));
                passenger.setPhoneNumber(rs.getString("phone"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return passenger;
    }

    public List<Trip> getPastTrip(int passengerId){
        List<Trip> trips = new ArrayList<>();
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String infoQuery = "Select T.train_id as trainId," +
                    "       T.route_id as routeId," +
                    "       D.date as fromDate," +
                    "       A.date as toDate," +
                    "       AS.station_name as toSation," +
                    "       DS.station_name as fromStation," +
                    "       T.carriage_num as carNum," +
                    "       T.ticket_id as ticketId," +
                    "       T.seat_num as seat," +
                    "       R.route_name" +
                    "       from Ticket T," +
                    "            Departure D," +
                    "            Arrival A," +
                    "            Station AS," +
                    "            Station DS," +
                    "            Route R" +
                    "            where T.Passenger_passenger_id = " + passengerId +
                    "            and T.route_id = R.route_id" +
                    "            and DS.station_id = T.station_from " +
                    "            and D.station_id = DS.station_id" +
                    "            and AS.station_id = T.station_to" +
                    "            and A.station_id = AS.station_id" +
                    "            and T.route_start_date < curdate() " +
                    "            order by fromDate desc;";
            ResultSet rs = myStatement.executeQuery(infoQuery);

            while(rs.next()){
                try{
                    Trip trip = setTrip(rs);
                    trips.add(trip);
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return trips;
    }
    private Trip setTrip(ResultSet rs) throws SQLException {
        Trip trip = new Trip();
        trip.setCarriageNum(rs.getInt("carNum"));
        trip.setTrainId(rs.getInt("trainId"));
        trip.setRouteId(rs.getInt("routeId"));
        trip.setFromDate(rs.getDate("fromDate"));
        trip.setToDate(rs.getDate("toDate"));
        trip.setToStation(rs.getString("toStation"));
        trip.setFromStation(rs.getString("fromStation"));
        trip.setTicketId(rs.getInt("ticketId"));
        trip.setSeat(rs.getInt("seat"));
        return trip;
    }
    public List<Trip> getNextTrip(int passengerId){
        List<Trip> trips = new ArrayList<>();
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String infoQuery = "Select T.train_id as trainId," +
                    "       T.route_id as routeId," +
                    "       D.date as fromDate," +
                    "       A.date as toDate," +
                    "       AS.station_name as toSation," +
                    "       DS.station_name as fromStation," +
                    "       T.carriage_num as carNum," +
                    "       T.ticket_id as ticketId," +
                    "       T.seat_num as seat," +
                    "       R.route_name" +
                    "       from Ticket T," +
                    "            Departure D," +
                    "            Arrival A," +
                    "            Station AS," +
                    "            Station DS," +
                    "            Route R" +
                    "            where T.Passenger_passenger_id = " + passengerId +
                    "            and T.route_id = R.route_id" +
                    "            and DS.station_id = T.station_from " +
                    "            and D.station_id = DS.station_id" +
                    "            and AS.station_id = T.station_to" +
                    "            and A.station_id = AS.station_id" +
                    "            and T.route_start_date >=  curdate() " +
                    "            order by fromDate asc;";
            ResultSet rs = myStatement.executeQuery(infoQuery);

            while(rs.next()){
                try{
                    Trip trip = setTrip(rs);
                    trips.add(trip);
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return trips;
    }
}
