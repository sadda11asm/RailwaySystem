package railwaysProject.model.route;


import java.time.*;
public class newRoute {
    private String routeName;
    private int carNum;
    private int seatNum;
    private LocalDate[] dates;
    private LocalTime startTime;
    private stationDuration[] stations;
    private int LastStation;


    public newRoute(String name, int carNum, int seatNum, LocalDate[] dates, LocalTime startTime, stationDuration[] stations, int LastStation){
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

    public stationDuration[] getStations() {
        return stations;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalDate[] getDates() {
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
