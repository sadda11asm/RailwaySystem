package railwaysProject.model.Employees;

import railwaysProject.model.Schedule.FinalSchedule;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

public class Adjustment {

    @JsonbProperty("schedule") private ReqSchedule[] schedule;

    @JsonbCreator()
    public Adjustment(@JsonbProperty("schedule")ReqSchedule[] schedule) {
        this.schedule = schedule;
    }

    public ReqSchedule[] getSchedule() {
        return schedule;
    }
}


