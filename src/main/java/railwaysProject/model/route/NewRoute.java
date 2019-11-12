package railwaysProject.model.route;


import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import java.time.*;
public class NewRoute {
    @JsonbProperty("routeName") private String routeName;
    @JsonbProperty("carNum") private int carNum;
    @JsonbProperty("seatNum") private int seatNum;
    //private LocalDate[] dates;
    @JsonbProperty("dates") private String[] dates;
    //private LocalTime startTime;
    @JsonbProperty("startTime") private String startTime;
    @JsonbProperty("stations") private StationDuration[] stations;
    @JsonbProperty("LastStation") private int LastStation;

    @JsonbCreator()
    public NewRoute(@JsonbProperty("routeName") String name,
                    @JsonbProperty("carNum") int carNum,
                    @JsonbProperty("seatNum") int seatNum,
                    @JsonbProperty("dates") String[] dates,
                    @JsonbProperty("startTime") String startTime,
                    @JsonbProperty("stations") StationDuration[] stations,
                    @JsonbProperty("LastStation") int LastStation){
        routeName = name;
        this.carNum = carNum;
        this.seatNum = seatNum;
        this.dates = dates;
        this.startTime = startTime;
        this.stations = stations;
        this.LastStation = LastStation;
    }

    public int getLastStation() {
        return LastStation;
    }

    public StationDuration[] getStations() {
        return stations;
    }

    public String getStartTime() {
        return startTime;
    }

    public String[] getDates() {
        return dates;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public int getCarNum() {
        return carNum;
    }

    public String getRouteName() {
        return routeName;
    }
}
