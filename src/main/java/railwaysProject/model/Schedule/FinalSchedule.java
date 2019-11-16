package railwaysProject.model.Schedule;

public class FinalSchedule {
    private int e_id;
    private int week_day;
    private int start_hour;
    private int end_hour;

    public int getE_id() {
        return e_id;
    }

    public int getWeek_day() {
        return week_day;
    }

    public int getStart_hour() {
        return start_hour;
    }

    public int getEnd_hour() {
        return end_hour;
    }

    public FinalSchedule(int e_id, int week_day, int start_hour, int end_hour) {
        this.e_id = e_id;
        this.week_day = week_day;
        this.start_hour = start_hour;
        this.end_hour = end_hour;
    }

    public void setE_id(int e_id) {
        this.e_id = e_id;
    }

    public void setWeek_day(int week_day) {
        this.week_day = week_day;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "e_id=" + e_id +
                ", week_day=" + week_day +
                ", start_hour=" + start_hour +
                ", end_hour=" + end_hour +
                '}';
    }


}
