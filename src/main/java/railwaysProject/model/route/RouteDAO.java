package railwaysProject.model.route;


import railwaysProject.model.BookRequest;
import railwaysProject.model.TicketEntity;
import railwaysProject.model.seat.Seat;
import railwaysProject.model.seat.SeatEntity;

import java.util.List;

public interface RouteDAO {

    public List<Route> getRoutesFromTo(int from, int to, String date);

    List<CityRoute> getArrivalToTheCity(int cityId);

    List<CityRoute> getDepartureFromTheCity(int cityId);

    List<SeatEntity> getAllSeats(String route_id);

    List<TicketEntity> getBookedSeats(String route_id, String date);

    boolean bookTicket(BookRequest request);
}
