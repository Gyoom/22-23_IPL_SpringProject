package be.vinci.ipl.chattycar.trips;

import be.vinci.ipl.chattycar.trips.models.NewTrip;
import be.vinci.ipl.chattycar.trips.models.Trip;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
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
   * @return The trip if the trip could be created, null if another trip exists with this pseudo
   */
  public Trip createOne(NewTrip newTrip) {
    System.out.println("service trip");
    if (repository.existsByOriginAndDestinationAndDepartureAndDriverId(newTrip.getOrigin(), newTrip.getDestination(), newTrip.getDeparture(),
        newTrip.getDriverId())) return null;
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
   * Returns the list of the last 20 recorded trips of the database,
   * for which there is at least 1 free space left and which has been filtered
   * according to the parameters provided with the request.
   * @param departure_date : the date of the trips to keep.
   * @param originLat : the latitude of the trips origin to keep.
   * @param originLon : the longitude of the trip origin to keep.
   * @param destinationLat : the latitude of the trip destination to keep.
   * @param destinationLon : the longitude of the trip destination to keep.
   * @return trips found and sorted
   */
  public Iterable<Trip> readAll(
      String departure_date,
      Double originLat,
      Double originLon,
      Double destinationLat,
      Double destinationLon) {
    Iterable<Trip> trips = StreamSupport.stream(repository.findAll().spliterator(), false)
        .filter(trip -> trip.getAvailableSeat() > 0)
        .collect(Collectors.toList());
    return filterAll(
        trips,
        departure_date,
        originLat,
        originLon,
        destinationLat,
        destinationLon
    );
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
   * Returns the list of the last 20 recorded trips for which the user with the identifier
   * is the driver, for which there is at least 1 free space left and which has been filtered
   * according to the parameters provided with the request.
   * @param id int of the driver
   * @param departure_date : the date of the trips
   * @param originLat : the latitude of the trips origin
   * @param originLon : the longitude of the trips origin
   * @param destinationLat : the latitude of the trips destination
   * @param destinationLon : the longitude of the trips destination
   * @return the list of trips filtered.
   */
  public Iterable<Trip> readAllThoseDriver(
      int id,
      String departure_date,
      Double originLat,
      Double originLon,
      Double destinationLat,
      Double destinationLon
  ) {
    Iterable<Trip> trips = StreamSupport.stream(repository.findByDriverId(id).spliterator(), false)
        .filter(trip -> trip.getAvailableSeat() > 0)
        .collect(Collectors.toList());
    return filterAll(
        trips,
        departure_date,
        originLat,
        originLon,
        destinationLat,
        destinationLon
    );
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
   * Call the different filter methods on the trip list based on
   * the state of the provided parameters
   * @param trips : travel list to filter.
   * @param departure_date : the date of the trips to keep.
   * @param originLat : the latitude of the trips origin to keep.
   * @param originLon : the longitude of the trip origin to keep.
   * @param destinationLat : the latitude of the trip destination to keep.
   * @param destinationLon : the longitude of the trip destination to keep.
   * @return
   */
  private Iterable<Trip> filterAll(
      Iterable<Trip> trips,
      String departure_date,
      Double originLat,
      Double originLon,
      Double destinationLat,
      Double destinationLon
  ) {
    if (departure_date != null)
      trips = departureDateFilter(trips, departure_date);
    if (originLat != null && originLon != null)
      trips = originFilter(trips, originLat, originLon);
    if (destinationLat != null && destinationLon != null)
      trips = destinationFilter(trips, destinationLat, destinationLon);
    trips = StreamSupport.stream(trips.spliterator(), false)
        .collect(lastN(20));
    return trips;
  }

  /**
   * filters the list of trips provided in param by keeping only
   * those whose date corresponds to that provided in param.
   * @param trips : list of trips to filter.
   * @param departure_date : date to the trips to keep.
   * @return the list filtered.
   */
  private Iterable<Trip> departureDateFilter(Iterable<Trip> trips, String departure_date) {
    return StreamSupport.stream(trips.spliterator(), false)
        .filter(trip -> trip.getDeparture().equals(departure_date))
        .collect(Collectors.toList());
  }

  /**
   * Filters the list provided in param with the trip origin data
   * provided in param and returns the sorted list.
   * @param trips : list of trips to filter.
   * @param originLat : latitude of trips origin to keep.
   * @param originLon : longitude of trips origins to keep.
   * @return the list filtered.
   */
  private Iterable<Trip> originFilter(Iterable<Trip> trips, Double originLat, Double originLon) {
    System.out.println(originLat);
    return StreamSupport.stream(trips.spliterator(), false)
        .filter(trip -> trip.getOrigin().getLatitude().equals(originLat)
            && trip.getOrigin().getLongitude().equals(originLon))
        .collect(Collectors.toList());
  }

  /**
   * Filters the list provided in param with the trip origin
   * data provided in param and returns the sorted list.
   * @param trips : list of trips to filter.
   * @param destinationLat : latitude of trips destination to keep.
   * @param destinationLon : longitude of trips destination to keep.
   * @return the list filtered.
   */
  private Iterable<Trip> destinationFilter(Iterable<Trip> trips, Double destinationLat, Double destinationLon) {
    return StreamSupport.stream(trips.spliterator(), false)
        .filter(trip -> trip.getDestination().getLatitude().equals(destinationLat)
            && trip.getDestination().getLongitude().equals(destinationLon))
        .collect(Collectors.toList());
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
