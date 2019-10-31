package railwaysProject.model.Passengers;

import railwaysProject.util.ConnectionPool;

import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerDaoImpl implements PassengerDAO {
    @Override
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
                        lists.getString("phone#")
                ));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return passengers.size() > 0 ? passengers.get(0) : null;
    }

    @Override
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

    @Override
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

    @Override
    public int signUpUser(String email, String firstName, String lastName, String password) {
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
               return (int) tableKeys.getLong(1);
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
