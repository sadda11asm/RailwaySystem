package railwaysProject.model.route;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import java.time.*;
public class StationDuration {

    @JsonbProperty("stationId") private int stationId;
    //private LocalTime duration;
    @JsonbProperty("duration") private String duration;

    @JsonbCreator()
    public StationDuration( @JsonbProperty("stationId") int id, @JsonbProperty("duration") String dur){
        stationId = id;
        duration = dur;

    }

    public int getStationId() {
        return stationId;
    }

    public String getDuration() {
        return duration;
    }
}
