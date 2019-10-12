package model.passenger;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.ws.rs.core.Response;

public interface PassengerDAO {

    public Response getPassenger(int passId);

}
