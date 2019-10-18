package railwaysProject.controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import railwaysProject.model.Cities.City;
import railwaysProject.util.ConnectionPool;

/* Java program to find a Pair which has maximum score*/
public class CityController {

    public List<City> getCities(){
        List<City> cities = new ArrayList<>();
        try {
            Connection myConnection = ConnectionPool.getDatabaseConnection();
            Statement myStatement = myConnection.createStatement();
            String query = "Select locality_id, locality_name from mydb.Locality order by locality_name asc;";
            ResultSet lists = myStatement.executeQuery(query);

            while(lists.next()){
                cities.add(new City(lists.getString("locality_name"), lists.getInt("locality_id")));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return cities;
    }

}
