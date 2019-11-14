package railwaysProject.model.Schedule;


public class Schedule extends FinalSchedule {

    private int hours_num;

    public Schedule(int e_id, int week_day, int start_hour, int hours_num) {
        super(e_id, week_day, start_hour, -1);
        this.hours_num = hours_num;
    }

    public void setHours_num(int hours_num) {
        this.hours_num = hours_num;
    }

    public int getHours_num() {
        return hours_num;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", hours_num=" + hours_num +
                '}';
    }

    public FinalSchedule toFinalSchedule(Schedule schedule) {
        return new FinalSchedule(schedule.getE_id(), schedule.getWeek_day(), schedule.getStart_hour(), (schedule.getStart_hour() + schedule.getHours_num())%24);
    }
}
