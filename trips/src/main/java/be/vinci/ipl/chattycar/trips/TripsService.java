package be.vinci.ipl.chattycar.trips;

import be.vinci.ipl.chattycar.trips.models.NewTrip;
import be.vinci.ipl.chattycar.trips.models.Trip;
import com.google.common.util.concurrent.Striped;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class TripsService {

  private final TripsRepository repository;

  public TripsService(TripsRepository repository) {
    this.repository = repository;
  }

  /**
   * Creates a trip
   * @param newTrip NewTrip to create
   * @return The trip if the trip could be created, null if another trip exists with this pseudo
   */
  public Trip createOne(NewTrip newTrip) {
    if (repository.existsByOriginAndDestinationAndDepartureAndDriverId(newTrip.getOrigin(), newTrip.getDestination(), newTrip.getDeparture(),
        newTrip.getDriver_id())) return null;
    return repository.save(newTrip.toTrip());
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
   * returns the list of the last 20 registered trips for which there is at least 1 free seat.
   * @return All trips found.
   */
  public Iterable<Trip> readAll() {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .filter(trip -> trip.getAvailableSeat() > 0)
        .collect(lastN(20));
  }

  /**
   * Returns the list of the last 20 recorded trips for which there is at least
   * 1 free seat left and for which the date passed in param match.
   * @param departure_date trip departure date
   * @return an iterable element containing a list meeting the above criteria.
   */
  public Iterable<Trip> readWithDepartureDate(String departure_date) {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .filter(trip -> trip.getDeparture().equals(departure_date))
        .filter(trip -> trip.getAvailableSeat() > 0)
        .collect(lastN(20));
  }

  /**
   * Returns the list of the last 20 registered trips for which there is at least 1 free seat
   * left and for which the coordinates of the destination of the trip correspond.
   * @param originLat the latitude of the coordinate indicating the origin of the trip.
   * @param originLon the longitude of the coordinate indicating the origin of the trip.
   * @return an iterable element containing a list meeting the above criteria.
   */
  public Iterable<Trip> readWithOrigin(float originLat, float originLon) {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .filter(trip -> trip.getOrigin().getLatitude() == originLat
            && trip.getOrigin().getLongitude() == originLon)
        .filter(trip -> trip.getAvailableSeat() > 0)
        .collect(lastN(20));
  }

  /**
   * Returns the list of the last 20 registered trips for which there is at least 1 free seat
   * left and for which the coordinates of the destination of the trip correspond.
   * @param destinationLat the latitude of the coordinate indicating the destination of the trip.
   * @param destinationLon the longitude of the coordinate indicating the destination of the trip.
   * @return an iterable element containing a list meeting the above criteria.
   */
  public Iterable<Trip> readWithDestination(float destinationLat, float destinationLon) {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .filter(trip -> trip.getDestination().getLatitude() == destinationLat
            && trip.getDestination().getLongitude() == destinationLon)
        .filter(trip -> trip.getAvailableSeat() > 0)
        .collect(lastN(20));
  }


  /**
   * Deletes a trip
   * @param id int of the trip
   * @return True if the trip could be deleted, false if the trip couldn't be found
   */
  public Trip deleteOne(int id) {
    Trip trip = repository.findById(id).orElse(null);
    if (trip == null) return null;
    repository.deleteById(id);
    return trip;
  }

  /**
   * Returns the list of the last 20 registered trips for which the user with
   * the id is the driver and for which there is at least 1 free seat.
   * @param id int of the driver
   * @return All the trip found.
   */
  public Iterable<Trip> readAllThoseDriver(int id) {
    return StreamSupport.stream(repository.findByDriverId(id).spliterator(), false)
        .filter(trip -> trip.getAvailableSeat() > 0)
        .collect(lastN(20));
  }

  /**
   * Returns the list of the last 20 recorded trips for which the user with
   * the id is the driver, for which there is at least 1 free seat left and
   * for which the date passed in param match.
   * @param id int of the driver
   * @param departure_date trip departure date
   * @return
   */
  public Iterable<Trip> readAllFromDriverWithDepartureDate(int id, String departure_date) {
    return StreamSupport.stream(repository.findByDriverId(id).spliterator(), false)
        .filter(trip -> trip.getDeparture().equals(departure_date))
        .filter(trip -> trip.getAvailableSeat() > 0)
        .collect(lastN(20));
  }

  /**
   * Returns the list of the last 20 registered trips for which the user with the id is the driver,
   * for which there is at least 1 free seat left and for which the coordinates of the origin
   * of the trip correspond.
   * @param id int of the driver
   * @param originLat the latitude of the coordinate indicating the origin of the trip.
   * @param originLon the longitude of the coordinate indicating the origin of the trip.
   * @return an iterable element containing a list meeting the above criteria.
   */
  public Iterable<Trip> readAllThoseDriverWithOrigin(int id, float originLat, float originLon) {
    return StreamSupport.stream(repository.findByDriverId(id).spliterator(), false)
        .filter(trip -> trip.getOrigin().getLatitude() == originLat
            && trip.getOrigin().getLongitude() == originLon)
        .filter(trip -> trip.getAvailableSeat() > 0)
        .collect(lastN(20));
  }

  /**
   * Returns the list of the last 20 registered trips for which the user with the id is the driver,
   * for which there is at least 1 free seat left and for which the coordinates of the destination
   * of the trip correspond.
   * @param id int of the driver
   * @param destinationLat the latitude of the coordinate indicating the destination of the trip.
   * @param destinationLon the longitude of the coordinate indicating the destination of the trip.
   * @return an iterable element containing a list meeting the above criteria.
   */
  public Iterable<Trip> readAllThoseDriverWithDestination(int id, float destinationLat, float destinationLon) {
    return StreamSupport.stream(repository.findByDriverId(id).spliterator(), false)
        .filter(trip -> trip.getDestination().getLatitude() == destinationLat
            && trip.getDestination().getLongitude() == destinationLon)
        .filter(trip -> trip.getAvailableSeat() > 0)
        .collect(lastN(20));
  }

  /**
   * Delete all the driver list trips.
   * @param id int of the driver
   * @return All the trip found.
   */
  public Iterable<Trip> deleteAllByDriver(int id) {
    return repository.deleteByDriverId(id);
  }

  /**
   * This method builds a collector that allows
   * a stream to return the last n elements that compose it.
   * @param n number of items returned
   * @return a collector that allows a stream to return
   * the last n elements that compose it.
   */
  private static <T> Collector<T, ?, List<T>> lastN(int n) {
    return Collector.<T, Deque<T>, List<T>>of(ArrayDeque::new, (acc, t) -> {
      if(acc.size() == n)
        acc.pollFirst();
      acc.add(t);
    }, (acc1, acc2) -> {
      while(acc2.size() < n && !acc1.isEmpty()) {
        acc2.addFirst(acc1.pollLast());
      }
      return acc2;
    }, ArrayList::new);
  }
}
