package railwaysProject.model.Employees;

import railwaysProject.model.Schedule.FinalSchedule;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

public class Adjustment {

    @JsonbProperty("schedule") private List<ReqSchedule> schedule;

    @JsonbCreator()
    public Adjustment(@JsonbProperty("schedule")List<ReqSchedule> schedule) {
        this.schedule = schedule;
    }

    public List<ReqSchedule> getSchedule() {
        return schedule;
    }
}


