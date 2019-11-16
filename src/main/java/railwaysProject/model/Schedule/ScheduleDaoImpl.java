package railwaysProject.model.Schedule;

import railwaysProject.model.route.CityRoute;
import railwaysProject.util.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ScheduleDaoImpl implements ScheduleDAO {
    @Override
    public ArrayList<Schedule> getSchedule(int e_id) {
        String newSqlQuery =    "SELECT * FROM Schedule WHERE e_id = " + e_id;

        Connection conn;
        ArrayList<Schedule> schedule = new ArrayList<>();
        try {
            conn = ConnectionPool.getDatabaseConnection();
            ResultSet result = conn.createStatement().executeQuery(newSqlQuery);
            while(result.next()) {
                int week_day = result.getInt("week_day");
                int start_hour = result.getInt("start_hour");
                int hours_num = result.getInt("hours_num");
                Schedule row = new Schedule(e_id, week_day, start_hour, hours_num);
//                System.out.println(row.toString());
                schedule.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();


        return schedule;
    }
}
