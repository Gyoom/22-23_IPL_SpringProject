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
import org.springframework.http.HttpStatus;
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

  public HttpStatus createPassenger(int tripsId, int userId) {
    Trip trip = tripsProxy.readTrip(tripsId);
    if (trip == null || usersProxy.readUser(userId) == null)
      return HttpStatus.NOT_FOUND;

    Passenger passenger = repository.findPassengerByTripIdAndUserId(tripsId, userId);

    if (passenger != null || trip.getAvailable_seating() == 0)
      return HttpStatus.BAD_REQUEST;

    Passenger newPassenger = new Passenger();
    newPassenger.setTripId(tripsId);
    newPassenger.setUserId(userId);
    newPassenger.setStatus("pending");
    repository.save(newPassenger);

    return HttpStatus.CREATED;
  }

  public String getPassengerStatus(int tripsId, int userId) {
    Passenger passenger = repository.findPassengerByTripIdAndUserId(tripsId, userId);
    if (passenger == null) return null;
    return passenger.getStatus();
  }

  public HttpStatus updatePassengerStatus(int tripsId, int userId, String status) {

    if (tripsProxy.readTrip(tripsId) == null || usersProxy.readUser(userId) == null)
      return HttpStatus.NOT_FOUND;

    Passenger passenger = repository.findPassengerByTripIdAndUserId(tripsId, userId);

    System.out.println(passenger);

    if (passenger == null || !passenger.getStatus().equals("pending")) return HttpStatus.BAD_REQUEST;

    passenger.setStatus(status);
    repository.save(passenger);

    return HttpStatus.OK;
  }

  public PassengerTrips getPassengerTrips(int userId) {
    if (usersProxy.readUser(userId) == null) return null;

    List<Passenger> passengerListOfUser = repository.findAllByUserId(userId);

    PassengerTrips passengerTrips = new PassengerTrips();
    passengerTrips.setAccepted(passengersToTrips(passengerListOfUser, "accepted"));
    passengerTrips.setRefused(passengersToTrips(passengerListOfUser, "refused"));
    passengerTrips.setPending(passengersToTrips(passengerListOfUser, "pending"));

    return passengerTrips;
  }

  public Passengers getTripPassengers(int tripId) {
    if (tripsProxy.readTrip(tripId) == null) return null;

    List<Passenger> passengerListOfTrip = repository.findAllByTripId(tripId);

    Passengers tripPassengers = new Passengers();
    tripPassengers.setAccepted(passengersToUsers(passengerListOfTrip, "accepted"));
    tripPassengers.setRefused(passengersToUsers(passengerListOfTrip, "refused"));
    tripPassengers.setPending(passengersToUsers(passengerListOfTrip, "pending"));

    return tripPassengers;
  }

  private List<Trip> passengersToTrips(List<Passenger> passengers, String status) {
    return passengers
        .stream()
        .filter(p -> p.getStatus().equals(status))
        .map(p -> tripsProxy.readTrip(p.getTripId()))
        .toList();
  }

  private List<User> passengersToUsers(List<Passenger> passengers, String status) {
    return passengers
        .stream()
        .filter(p -> p.getStatus().equals(status))
        .map(p -> usersProxy.readUser(p.getUserId()))
        .toList();
  }

}
