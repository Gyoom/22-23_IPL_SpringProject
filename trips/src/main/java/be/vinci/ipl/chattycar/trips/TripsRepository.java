package be.vinci.ipl.chattycar.trips;

import be.vinci.ipl.chattycar.trips.models.Position;
import be.vinci.ipl.chattycar.trips.models.Trip;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripsRepository extends CrudRepository<Trip, Integer> {

  boolean existsByOriginAndDestinationAndDepartureAndDriver_id(Position origin, Position destination, String departure, int driverId);

  Iterable<Trip> findByDriver_id(int driverId);

  Iterable<Trip> deleteByDriver_id(int driverId);

}
