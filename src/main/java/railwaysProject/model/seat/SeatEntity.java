package railwaysProject.model.seat;

public class SeatEntity implements Comparable {

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
        if (obj instanceof SeatEntity) {
            SeatEntity s = (SeatEntity) obj;
            System.out.println(s.toString() + this.toString());
            return seatNum == s.seatNum && routeId == s.routeId && carriageNum == s.carriageNum && trainId == s.trainId;
        }
        System.out.println("NOT A SEATENTITY " + obj.toString());

        return false;
    }

    @Override
    public int hashCode() {
        return seatNum*13 + routeId*17 + carriageNum*7 + trainId*11;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof SeatEntity) {
            SeatEntity obj = (SeatEntity) o;
            return seatNum < obj.seatNum ? 0 : 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "SeatEntity{" +
                "seatNum=" + seatNum +
                ", routeId=" + routeId +
                ", carriageNum=" + carriageNum +
                ", trainId=" + trainId +
                '}';
    }
}
