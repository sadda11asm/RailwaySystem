package model.passenger;

import util.Database;
import util.StatusMessage;

import javax.sql.DataSource;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PassengerDaoImpl implements PassengerDAO{

    private static final Logger LOGGER = Logger.getLogger(PassengerDaoImpl.class.getName());
    private DataSource datasource = Database.getDataSource();

    @Override
    public Response getPassenger(int passId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Passenger passenger = null;
        String sql = "select pass_id, name, last_name"
                + "from Passenger "
                + "where pass_id = ?";
        try {
            conn = datasource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, passId);
            rs = ps.executeQuery();
            if (rs.next()) {
                passenger = new Passenger();
                passenger.setPassId(rs.getInt("pass_id"));
                passenger.setFirstName(rs.getString("first_name"));
                passenger.setLastName(rs.getString("last_name"));
            } else {
                LOGGER.log(Level.WARNING, String.format("Customer with ID of %d is not found.", passId));
                StatusMessage statusMessage = new StatusMessage();
                statusMessage.setStatus(Response.Status.NOT_FOUND.getStatusCode());
                statusMessage.setMessage(
                        String.format("Passenger with pass_id of %d is not found.", passId));
                return Response.status(404).entity(statusMessage).build();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, "Error closing resultset: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, "Error closing PreparedStatement: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, "Error closing connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return Response.status(200).entity(passenger).build();

    }
}