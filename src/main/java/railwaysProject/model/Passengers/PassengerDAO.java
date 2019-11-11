package railwaysProject.model.Passengers;

import javax.ws.rs.core.Response;
import java.util.List;

public interface PassengerDAO {
    Passenger getUserByEmailAndPassword(String email, String password);
    List<Passenger> getAllUsers();
    Passenger getUserByEmail(String email);

    int signUpUser(String email, String firstName, String lastName, String password);
}
