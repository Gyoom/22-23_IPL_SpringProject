package be.vinci.ipl.chattycar.trips;

import be.vinci.ipl.chattycar.trips.models.NewTrip;
import be.vinci.ipl.chattycar.trips.models.Trip;
import org.springframework.stereotype.Service;

@Service
public class TripsService {

  private final TripsRepository repository;

  public TripsService(TripsRepository repository) {
    this.repository = repository;
  }

  /**
   * Creates a trip
   * @param newTrip NewTrip to create
   * @return true if the trip could be created, false if another trip exists with this pseudo
   */
  public boolean createOne(NewTrip newTrip) {
    if (repository.existsByOriginAndDestinationAndDepartureAndDriver_id(newTrip.getOrigin(), newTrip.getDestination(), newTrip.getDeparture(),
        newTrip.getDriver_id())) return false;
    repository.save(newTrip.toTrip());
    return true;
  }

  /**
   * Reads a trip
   * @param id int of the trip
   * @return The trip found, or null if the trip couldn't be found
   */
  public Trip readOne(int id) {
    return repository.findById(id).orElse(null);
  }

  /**
   * Reads all trips
   * @return All trips found.
   */
  public Iterable<Trip> readAll() {
    return repository.findAll();
  }


  /**
   * Deletes a trip
   * @param id int of the trip
   * @return True if the trip could be deleted, false if the trip couldn't be found
   */
  public boolean deleteOne(int id) {
    if (!repository.existsById(id)) return false;
    repository.deleteById(id);
    return true;
  }

  /**
   * Reads the driver list trips.
   * @param id int of the driver
   * @return All the trip found.
   */
  public Iterable<Trip> readOneByDriver(int id) {
    return repository.findByDriver_id(id);
  }

}
