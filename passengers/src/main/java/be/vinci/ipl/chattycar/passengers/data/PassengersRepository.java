package be.vinci.ipl.chattycar.passengers.data;

import be.vinci.ipl.chattycar.passengers.models.Passenger;
import be.vinci.ipl.chattycar.passengers.models.Trip;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengersRepository extends CrudRepository<Passenger, Long> {
  Passenger findPassengerByTripIdAndUserId(int tripId, int userId);

  List<Passenger> findAllByUserId(int userId);
  List<Passenger> findAllByTripId(int tripId);
}
