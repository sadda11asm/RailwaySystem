package railwaysProject.model.seat;

public class SeatEntity {

    private int seatNum;
    private int routeId;
    private int carriageNum;
    private int trainId;

    public SeatEntity(int seatNum, int routeId, int carriageNum, int trainId) {
        this.seatNum = seatNum;
        this.routeId = routeId;
        this.carriageNum = carriageNum;
        this.trainId = trainId;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public int getRouteId() {
        return routeId;
    }

    public int getCarriageNum() {
        return carriageNum;
    }

    public int getTrainId() {
        return trainId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  SeatEntity) {
            SeatEntity s = (SeatEntity) obj;
            return seatNum == s.seatNum && routeId == s.routeId && carriageNum == s.carriageNum && trainId == s.trainId;
        }

        return false;
    }
}
