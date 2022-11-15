package be.vinci.ipl.chattycar.passengers;

import be.vinci.ipl.chattycar.passengers.data.PassengersRepository;
import be.vinci.ipl.chattycar.passengers.data.TripsProxy;
import be.vinci.ipl.chattycar.passengers.data.UsersProxy;
import be.vinci.ipl.chattycar.passengers.models.Passenger;
import be.vinci.ipl.chattycar.passengers.models.PassengerTrips;
import be.vinci.ipl.chattycar.passengers.models.Passengers;
import be.vinci.ipl.chattycar.passengers.models.Trip;
import be.vinci.ipl.chattycar.passengers.models.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PassengersServices {
  private final PassengersRepository repository;
  private final TripsProxy tripsProxy;
  private final UsersProxy usersProxy;

  public PassengersServices(PassengersRepository repository, TripsProxy tripsProxy, UsersProxy usersProxy) {
    this.repository = repository;
    this.tripsProxy = tripsProxy;
    this.usersProxy = usersProxy;
  }

  /**
   * Add user as passenger to a trip with pending status.
   *
   * @param tripsId The id of a trip
   * @param userId The id of a user
   * @return true if the passenger has been created
   */
  public boolean createPassenger(int tripsId, int userId) {
    Trip trip = tripsProxy.readTrip(tripsId);
    Passenger passenger = repository.findPassengerByTripIdAndUserId(tripsId, userId);
    if (passenger != null || trip == null || trip.getAvailable_seating() == 0) return false;

    repository.save(new Passenger(userId, tripsId));
    return true;
  }

  /**
   * Get passenger status in relation to a trip.
   *
   * @param tripsId The id of a trip
   * @param userId The id of a user
   * @return the status if the passenger has been found or null
   */
  public String getPassengerStatus(int tripsId, int userId) {
    Passenger passenger = repository.findPassengerByTripIdAndUserId(tripsId, userId);
    if (passenger == null) return null;
    return passenger.getStatus();
  }

  /**
   * Update passenger status.
   *
   * @param tripsId The id of a trip
   * @param userId The id of a user
   * @param status The new status {"accepted" or "refused"}
   * @return true if the status has been updated or false
   */
  public boolean updatePassengerStatus(int tripsId, int userId, String status) {
    if (!status.equals("accepted") && !status.equals("refused"))
      return false;

    Passenger passenger = repository.findPassengerByTripIdAndUserId(tripsId, userId);
    if (passenger == null || !passenger.getStatus().equals("pending"))
      return false;

    passenger.setStatus(status);
    repository.save(passenger);
    return true;
  }

  /**
   * Get trips where user is a passenger with a future departure date by status.
   *
   * @param userId The id of a user
   * @return all the trips of a user by status
   */
  public PassengerTrips getPassengerTrips(int userId) {
    List<Passenger> passengerListOfUser = repository.findAllByUserId(userId);
    PassengerTrips passengerTrips = new PassengerTrips();
    passengerTrips.setAccepted(passengersToTrips(passengerListOfUser, "accepted"));
    passengerTrips.setRefused(passengersToTrips(passengerListOfUser, "refused"));
    passengerTrips.setPending(passengersToTrips(passengerListOfUser, "pending"));
    return passengerTrips;
  }

  /**
   * Remove all of a user's participation from a trip.
   *
   * @param userId The id of a user
   */
  public void removeAllParticipation(int userId) {
    repository.deleteAllByUserId(userId);
  }

  /**
   * Get list of passengers of a trip, with pending, accepted and refused status.
   *
   * @param tripId The id of a trip
   * @return all the passengers by status
   */
  public Passengers getTripPassengers(int tripId) {
    List<Passenger> passengerListOfTrip = repository.findAllByTripId(tripId);

    Passengers tripPassengers = new Passengers();
    tripPassengers.setAccepted(passengersToUsers(passengerListOfTrip, "accepted"));
    tripPassengers.setRefused(passengersToUsers(passengerListOfTrip, "refused"));
    tripPassengers.setPending(passengersToUsers(passengerListOfTrip, "pending"));
    return tripPassengers;
  }

  /**
   * Remove all passengers of a trip.
   *
   * @param tripId The id of a trip
   */
  public void removeAllPassenger(int tripId) {
    repository.deleteAllByTripId(tripId);
  }

  /**
   * Retrieve trips' properties from passenger object.
   *
   * @param passengers a list of passenger
   * @param status the status you want to have list of
   * @return a list of trip
   */
  private List<Trip> passengersToTrips(List<Passenger> passengers, String status) {
    return passengers
        .stream()
        .filter(p -> p.getStatus().equals(status))
        .map(p -> tripsProxy.readTrip(p.getTripId()))
        .toList();
  }

  /**
   * Retrieve users' properties from passenger object.
   *
   * @param passengers a list of passenger
   * @param status the status you want to have list of
   * @return a list of user
   */
  private List<User> passengersToUsers(List<Passenger> passengers, String status) {
    return passengers
        .stream()
        .filter(p -> p.getStatus().equals(status))
        .map(p -> usersProxy.readUser(p.getUserId()))
        .toList();
  }
}