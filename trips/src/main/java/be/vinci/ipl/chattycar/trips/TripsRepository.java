package be.vinci.ipl.chattycar.trips;

import be.vinci.ipl.chattycar.trips.models.Position;
import be.vinci.ipl.chattycar.trips.models.Trip;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripsRepository extends CrudRepository<Trip, Integer> {

  boolean existsByOriginAndDestinationAndDepartureAndDriverId(Position origin, Position destination, String departure, int driverId);

  Iterable<Trip> findByDriverId(int driverId);

  @Transactional
  Iterable<Trip> deleteByDriverId(int driverId);

}
