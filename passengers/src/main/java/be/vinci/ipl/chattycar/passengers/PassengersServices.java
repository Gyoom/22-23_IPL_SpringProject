package be.vinci.ipl.chattycar.passengers;

import be.vinci.ipl.chattycar.passengers.data.PassengersRepository;
import be.vinci.ipl.chattycar.passengers.data.TripsProxy;
import be.vinci.ipl.chattycar.passengers.data.UsersProxy;
import be.vinci.ipl.chattycar.passengers.models.Passenger;
import be.vinci.ipl.chattycar.passengers.models.Trip;
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
    if (passenger == null || trip.getAvailable_seating() == 0)
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

    if (passenger == null || (!status.equals("accepted") && !status.equals("refused")) ||
        passenger.getStatus().equals("accepted")) return HttpStatus.BAD_REQUEST;

    passenger.setStatus(status);
    repository.save(passenger);

    return HttpStatus.ACCEPTED;
  }


}
