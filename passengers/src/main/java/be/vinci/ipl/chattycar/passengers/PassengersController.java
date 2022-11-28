package be.vinci.ipl.chattycar.passengers;

import be.vinci.ipl.chattycar.passengers.models.PassengerTrips;
import be.vinci.ipl.chattycar.passengers.models.Passengers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PassengersController {
  private final PassengersServices service;

  public PassengersController(PassengersServices service) {
    this.service = service;
  }

  /**
   * Add user as passenger to a trip with pending status.
   *
   * @param tripsId The id of a trip
   * @param userId The id of a user
   * @return 201 status code if created or 400
   */
  @PostMapping("/passengers/{trip_id}/{user_id}")
  public ResponseEntity<Void> createPassenger(@PathVariable("trip_id") int tripsId,
      @PathVariable("user_id") int userId) {

    if (!service.createPassenger(tripsId, userId))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Get passenger status in relation to a trip.
   *
   * @param tripsId The id of a trip
   * @param userId The id of a user
   * @return 200 response status with the status if the passenger has been found or 400
   */
  @GetMapping("/passengers/{trip_id}/{user_id}")
  public String getPassengerStatus(@PathVariable("trip_id") int tripsId,
      @PathVariable("user_id") int userId) {

    String passengerStatus = service.getPassengerStatus(tripsId, userId);
    if (passengerStatus == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    return passengerStatus;
  }

  /**
   * Update passenger status.
   *
   * @param tripsId The id of a trip
   * @param userId The id of a user
   * @param status The new status {"accepted" or "refused"}
   */
  @PutMapping("/passengers/{trip_id}/{user_id}")
  public void updatePassengerStatus(@PathVariable("trip_id") int tripsId,
      @PathVariable("user_id") int userId, @RequestParam("status") String status) {

    if (!service.updatePassengerStatus(tripsId, userId, status))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
  }

  /**
   * Get trips where user is a passenger with a future departure date by status.
   *
   * @param userId The id of a user
   * @return 200 response status with all the trips of a user by status
   */
  @GetMapping("/passengers/user/{user_id}")
  public PassengerTrips getPassengerTrips(@PathVariable("user_id") int userId) {
    return service.getPassengerTrips(userId);
  }

  /**
   * Remove all of a user's participation from a trip.
   *
   * @param userId The id of a user
   */
  @DeleteMapping("/passengers/user/{user_id}")
  public void removeAllParticipation(@PathVariable("user_id") int userId) {
    service.removeAllParticipation(userId);
  }

  /**
   * Get list of passengers of a trip, with pending, accepted and refused status.
   *
   * @param tripId The id of a trip
   * @return 200 response status with all the passengers by status
   */
  @GetMapping("/passengers/trip/{trip_id}")
  public Passengers getTripPassengers(@PathVariable("trip_id") int tripId) {
    return service.getTripPassengers(tripId);
  }

  /**
   * Remove all passengers of a trip.
   *
   * @param tripId The id of a trip
   */
  @DeleteMapping("/passengers/trip/{trip_id}")
  public void removeAllPassenger(@PathVariable("trip_id") int tripId) {
    service.removeAllPassenger(tripId);
  }
}
