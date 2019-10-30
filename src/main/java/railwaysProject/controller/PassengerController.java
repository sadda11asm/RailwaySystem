package railwaysProject.controller;


import railwaysProject.model.Passengers.Passenger;
import railwaysProject.util.ConnectionPool;
import railwaysProject.model.trip.Trip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerController {
    public Passenger getUserByEmailAndPassword(String email, String password) {
        List<Passenger> passengers = new ArrayList<>();
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String query = "Select * from Passenger where email = '" + email + "' and password = '" + password + "'";
            ResultSet lists = myStatement.executeQuery(query);

            while(lists.next()){
                passengers.add(new Passenger(
                        lists.getInt("passenger_id"),
                        lists.getString("first_name"),
                        lists.getString("last_name"),
                        lists.getString("email"),
                        lists.getString("password"),
                        lists.getString("phone_number")
                ));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return passengers.size() > 0 ? passengers.get(0) : null;
    }

    public List<Passenger> getAllUsers() {
        List<Passenger> passengers = new ArrayList<>();
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String query = "Select * from Passenger";
            ResultSet lists = myStatement.executeQuery(query);

            while(lists.next()){
                passengers.add(new Passenger(
                        lists.getInt("passenger_id"),
                        lists.getString("first_name"),
                        lists.getString("last_name"),
                        lists.getString("email"),
                        lists.getString("password"),
                        lists.getString("phone_number")
                ));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return passengers;
    }

    public Passenger getUserByEmail(String email) {
        List<Passenger> passengers = new ArrayList<>();
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String query = "Select * from Passenger where email = '" + email+"'";
            ResultSet lists = myStatement.executeQuery(query);

            while(lists.next()){
                passengers.add(new Passenger(
                        lists.getInt("passenger_id"),
                        lists.getString("first_name"),
                        lists.getString("last_name"),
                        lists.getString("email"),
                        lists.getString("password"),
                        lists.getString("phone_number")
                ));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return passengers.size() > 0 ? passengers.get(0) : null;
    }

    public boolean signUpUser(String email,
                              String firstName,
                              String lastName,
                              String password) {
        try {
            System.out.println(email + ", " + firstName + ", " + lastName + ", " + password);
            System.out.println("INSERT into Passenger(first_name, last_name, email, password) VALUES ('" +
                    firstName +
                    "','" +
                    lastName +
                    "','" +
                    email +
                    "','" +
                    password +
                    "')");
            PreparedStatement preparedStatement = ConnectionPool.getDatabaseConnection()
                    .prepareStatement
                            ("INSERT into Passenger(first_name, last_name, email, password) VALUES ('" +
                                    firstName +
                                    "','" +
                                    lastName +
                                    "','" +
                                    email +
                                    "','" +
                                    password +
                                    "')", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            ResultSet tableKeys = preparedStatement.getGeneratedKeys();
            while(tableKeys.next()) {
                System.out.println(tableKeys);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
            String infoQuery = "Select ticket_id," +
                    "DS.station_name as dep_name," +
                    "AAS.station_name as arr_name," +
                    "A.date  as arr_date," +
                    "D.date as dep_date" +
                    "from Ticket," +
                    "Arrival as A," +
                    "Departure as D, " +
                    "Station as DS," +
                    "Station as AAS" +
                    "where pass_id =  " + passengerId + " and " +
                    "station_from = D.station_id and " +
                    "station_to = A.station_id and " +
                    "D.station_id = DS.station_id and A.station_id = AAS.station_id and " +
                    "D.date < current_date() " +
                    "order by D.date desc ;";
            ResultSet rs = myStatement.executeQuery(infoQuery);

            while(rs.next()){
                trips.add(new Trip(Integer.toString(rs.getInt("ticket_id")), rs.getString("dep_name"), rs.getString("arr_name"), rs.getDate("dep_date"), rs.getDate("arr_date") ));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return trips;
    }

    public List<Trip> getNextTrip(int passengerId){
        List<Trip> trips = new ArrayList<>();
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String infoQuery = "Select ticket_id," +
                    "DS.station_name as dep_name," +
                    "AAS.station_name as arr_name," +
                    "A.date  as arr_date," +
                    "D.date as dep_date" +
                    "from Ticket," +
                    "Arrival as A," +
                    "Departure as D, " +
                    "Station as DS," +
                    "Station as AAS" +
                    "where pass_id =  " + passengerId + " and " +
                    "station_from = D.station_id and " +
                    "station_to = A.station_id and " +
                    "D.station_id = DS.station_id and A.station_id = AAS.station_id and " +
                    "D.date >  current_date() " +
                    "order by D.date asc;";
            ResultSet rs = myStatement.executeQuery(infoQuery);

            while(rs.next()){
                trips.add(new Trip(Integer.toString(rs.getInt("ticket_id")), rs.getString("dep_name"), rs.getString("arr_name"), rs.getDate("dep_date"), rs.getDate("arr_date") ));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return trips;
    }
}
