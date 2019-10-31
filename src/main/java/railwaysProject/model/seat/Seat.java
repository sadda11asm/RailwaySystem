package railwaysProject.model.seat;

import java.util.List;

public class Seat {
    private int seat_num;
    private int carriage_num;
    boolean is_free;

    public Seat(int seatNum, int carriageNum, boolean is_free) {
        this.seat_num = seatNum;
        this.carriage_num = carriageNum;
        this.is_free = is_free;
    }
}
