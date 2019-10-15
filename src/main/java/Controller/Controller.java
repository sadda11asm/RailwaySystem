package Controller;

import com.google.gson.Gson;

import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/* Java program to find a Pair which has maximum score*/
public class Controller {

    public List<City> getCities(){
        List<City> cities = new ArrayList<>();
        String url = "URL  OF OUR DATABASE";
        String user = "root";                           // mb we will change it
        String password = "password of the root in database";
        try {
            Connection myConnection = DriverManager.getConnection(url, user, password);
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
