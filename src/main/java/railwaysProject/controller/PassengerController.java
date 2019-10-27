package railwaysProject.controller;


import railwaysProject.model.Passengers.Passenger;
import railwaysProject.model.Passengers.PassengerDAO;
import railwaysProject.model.route.RouteDAO;
import railwaysProject.util.ConnectionPool;

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
        return Response.ok().build();
    }
}
