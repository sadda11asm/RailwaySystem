package railwaysProject.model.route;
import java.time.*;
public class StationDuration {
    private int stationId;
    private LocalTime duration;
    public StationDuration(int id, LocalTime dur){
        stationId = id;
        duration = dur;

    }

    public int getStationId() {
        return stationId;
    }

    public LocalTime getDuration() {
        return duration;
    }
}
