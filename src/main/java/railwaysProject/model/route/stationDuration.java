package railwaysProject.model.route;
import java.time.*;
public class stationDuration {
    private int stationId;
    private LocalTime duration;
    public stationDuration(int id, LocalTime dur){
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
