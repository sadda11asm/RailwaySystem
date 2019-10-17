package railwaysProject.model.passenger;

import javax.ws.rs.core.Response;

public interface PassengerDAO {

    public Response getPassenger(int passId);

}
