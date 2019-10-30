package railwaysProject.model.Passengers;

import java.util.List;

public interface PassengerDAO {
    Passenger getUserByEmailAndPassword(String email, String password);
    List<Passenger> getAllUsers();
    Passenger getUserByEmail(String email);
}
