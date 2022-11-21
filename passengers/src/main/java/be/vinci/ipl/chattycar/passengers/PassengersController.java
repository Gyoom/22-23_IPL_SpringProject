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
   * @return 200 response status if the passenger has been added or 400
   */
  @PostMapping("/passengers/{trip_id}/{user_id}")
  public ResponseEntity<String> createPassenger(@PathVariable("trip_id") int tripsId,
      @PathVariable("user_id") int userId) {

    if (service.createPassenger(tripsId, userId)) {
      return new ResponseEntity<>(HttpStatus.CREATED);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  /**
   * Get passenger status in relation to a trip.
   *
   * @param tripsId The id of a trip
   * @param userId The id of a user
   * @return 200 response status with the status if the passenger has been found or 400
   */
  @GetMapping("/passengers/{trip_id}/{user_id}")
  public ResponseEntity<String> getPassengerStatus(@PathVariable("trip_id") int tripsId,
      @PathVariable("user_id") int userId) {

    String passengerStatus = service.getPassengerStatus(tripsId, userId);
    if (passengerStatus == null)
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(passengerStatus, HttpStatus.OK);
  }

  /**
   * Update passenger status.
   *
   * @param tripsId The id of a trip
   * @param userId The id of a user
   * @param status The new status {"accepted" or "refused"}
   * @return 200 response status if the status has been updated or 400
   */
  @PutMapping("/passengers/{trip_id}/{user_id}")
  public ResponseEntity<String> updatePassengerStatus(@PathVariable("trip_id") int tripsId,
      @PathVariable("user_id") int userId, @RequestParam("status") String status) {

    if (service.updatePassengerStatus(tripsId, userId, status))
      return new ResponseEntity<>(HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  /**
   * Get trips where user is a passenger with a future departure date by status.
   *
   * @param userId The id of a user
   * @return 200 response status with all the trips of a user by status
   */
  @GetMapping("/passengers/user/{user_id}")
  public ResponseEntity<PassengerTrips> getPassengerTrips(@PathVariable("user_id") int userId) {
    return new ResponseEntity<>(service.getPassengerTrips(userId), HttpStatus.OK);
  }

  /**
   * Remove all of a user's participation from a trip.
   *
   * @param userId The id of a user
   * @return 200 status code
   */
  @DeleteMapping("/passengers/user/{user_id}")
  public ResponseEntity<String> removeAllParticipation(@PathVariable("user_id") int userId) {
    service.removeAllParticipation(userId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Get list of passengers of a trip, with pending, accepted and refused status.
   *
   * @param tripId The id of a trip
   * @return 200 response status with all the passengers by status
   */
  @GetMapping("/passengers/trip/{trip_id}")
  public ResponseEntity<Passengers> getTripPassengers(@PathVariable("trip_id") int tripId) {
    return new ResponseEntity<>(service.getTripPassengers(tripId), HttpStatus.OK);
  }

  /**
   * Remove all passengers of a trip.
   *
   * @param tripId The id of a trip
   * @return 200 response status
   */
  @DeleteMapping("/passengers/trip/{trip_id}")
  public ResponseEntity<String> removeAllPassenger(@PathVariable("trip_id") int tripId) {
    service.removeAllPassenger(tripId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
