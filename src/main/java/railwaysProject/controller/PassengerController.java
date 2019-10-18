package railwaysProject.controller;


import railwaysProject.model.Passengers.Passenger;
import railwaysProject.util.ConnectionPool;

import javax.swing.plaf.nimbus.State;
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
}
