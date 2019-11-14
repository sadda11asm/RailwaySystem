package railwaysProject.model.Employees;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class ReqSchedule {
    @JsonbProperty("week_day") private int weekDay;
    @JsonbProperty("start_hour") private int startHour;
    @JsonbProperty("end_hour") private int endHour;


    @JsonbCreator()
    public ReqSchedule(@JsonbProperty ("week_day") int weekDay,
                       @JsonbProperty("start_hour") int startHour,
                       @JsonbProperty("end_hour") int endHour) {
        this.weekDay = weekDay;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
}
