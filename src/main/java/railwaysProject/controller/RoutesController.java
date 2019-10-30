package railwaysProject.controller;


import railwaysProject.model.route.CityRoute;
import railwaysProject.model.route.Route;
import railwaysProject.model.route.RouteDAO;
import railwaysProject.model.route.newRoute;
import railwaysProject.util.ConnectionPool;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class RoutesController {

    private RouteDAO routeDAO;

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

    private boolean routeExists(int routeID){
        Connection conn = ConnectionPool.getDatabaseConnection();
        boolean exists = false;
        try{
            Statement myStatement = conn.createStatement();
            String query = "Select * from Route where roure_id = " + routeID;
            ResultSet rs = myStatement.executeQuery(query);
            while(rs.next()){
                exists = true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return exists;
    }

    public newRoute crateNewRoute(String routeName, Date startDate){
        int routeId = new Random().nextInt(1000000) + 1;
        while(routeExists(routeId)){
            routeId = new Random().nextInt(1000000) + 1;
        }
        newRoute route =  new newRoute(routeId, startDate, routeName);
        insertRoute(route);



        return route;
    }



    private void insertRoute(newRoute route){
        Connection conn = ConnectionPool.getDatabaseConnection();

        try{
            Statement statement = conn.createStatement();
            String query = "INSERT INTO Route(route_id, start_date, route_name) values ("+ route.getRouteId() + ", "
                    + new java.sql.Date(route.getStartDate().getTime())+", '"+route.getRouteName()+"')";
            statement.execute(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
