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
        int passId = passengerDAO.signUpUser(email, firstName, lastName, password);
        if (passId == -1) {
            return Response.status(410).build();
        }
        return Response.ok(passId).build();
    }
}
