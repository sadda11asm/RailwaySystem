package railwaysProject.model.Passengers;

import railwaysProject.util.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
