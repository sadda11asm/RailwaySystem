package railwaysProject.controller;


import railwaysProject.model.BookRequest;
import railwaysProject.model.TicketEntity;
import railwaysProject.model.seat.Seat;
import railwaysProject.model.route.CityRoute;
import railwaysProject.model.route.Route;
import railwaysProject.model.route.RouteDAO;
import railwaysProject.model.seat.SeatEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class RoutesController {

    RouteDAO routeDAO;

    public RoutesController(RouteDAO routeDAO) {
        this.routeDAO = routeDAO;
    }


    protected List<Route> getRoutes(int from, int to, String date) {
        return routeDAO.getRoutesFromTo(from, to, date);
//        List<Route>
    }

    public List<CityRoute> getScheduleOfCity(int cityId) {

        List<CityRoute> routes = routeDAO.getArrivalToTheCity(cityId);


        List<CityRoute> depRoutes = routeDAO.getDepartureFromTheCity(cityId);

//        for (CityRoute route: depRoutes) {
//            if (finalRoutes.containsKey(new Pair<>(route.getRouteId(), new Pair(route.getStartDate(), route.getStationId())))) {
//                CityRoute r = finalRoutes.get(new Pair<>(route.getRouteId(), new Pair(route.getStartDate(), route.getStationId())));
//                r.setDepDate(route.getDepDate());
//            } else {
//                finalRoutes.put(new Pair<>(route.getRouteId(), new Pair(route.getStartDate(), route.getStationId())), route);
//            }
//        }

        for (int i = 0; i < routes.size(); i++) {
            for (int j = 0; j < depRoutes.size(); j++) {
                if (depRoutes.get(j).getRouteId() == routes.get(i).getRouteId() &&
                    depRoutes.get(j).getDateStart() == routes.get(i).getDateStart() &&
                        depRoutes.get(j).getStationId() == routes.get(i).getStationId()) {
                    routes.get(i).setDepDate(depRoutes.get(j).getDepDate());
                }
            }
        }
        if (routes.size()==0) return depRoutes;
        return routes;
    }

    public List<Route> getRoutesFromTo(int from, int to, String dateTo) {
        return routeDAO.getRoutesFromTo(from, to, dateTo);
    }


    private boolean check(String depDate, String arrDate, String start, String end) {
        if (depDate.compareTo(start)>=0 && depDate.compareTo(end)<=0) return false;
        if (arrDate.compareTo(start)<=0 && arrDate.compareTo(end)>=0) return false;
        if (depDate.compareTo(start)<0 && arrDate.compareTo(end)>0) return false;
        return true;
    }
    public List<Seat> getSeatsInfo(String route_id,  String date, String depDate, String arrDate) {
        List<SeatEntity> seats = routeDAO.getAllSeats(route_id);
        List<TicketEntity> tickets = routeDAO.getBookedSeats(route_id, date);
        Set<SeatEntity> booked = new TreeSet<>();

        for (int i = 0; i < tickets.size(); i++) {
            TicketEntity ticket = tickets.get(i);
            if (!check(ticket.getDepDate(), ticket.getArrDate(), depDate, arrDate)) {
                booked.add(new SeatEntity(ticket.getSeatNum(), ticket.getRouteId(), ticket.getCarriageNum(), ticket.getTrainId()));
            }
        }

        List<Seat> ans = new ArrayList<>();
        for (int i = 0; i < seats.size(); i++) {
            SeatEntity seat = seats.get(i);
            if (booked.contains(seat)) {
                ans.add(new Seat(seat.getSeatNum(), seat.getCarriageNum(), false));
            } else {
                ans.add(new Seat(seat.getSeatNum(), seat.getCarriageNum(), true));
            }
        }
        return ans;
    }

    public boolean bookTicket(BookRequest request) {
        return routeDAO.bookTicket(request);
    }
}
