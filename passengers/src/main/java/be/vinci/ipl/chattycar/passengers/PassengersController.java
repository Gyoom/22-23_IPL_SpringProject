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

  @PostMapping("/passengers/{trip_id}/{user_id}")
  public ResponseEntity<String> createPassenger(@PathVariable("trip_id") int tripsId,
      @PathVariable("user_id") int userId) {

    if (service.createPassenger(tripsId, userId)) {
      return new ResponseEntity<>(HttpStatus.CREATED);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping("/passengers/{trip_id}/{user_id}")
  public ResponseEntity<String> getPassengerStatus(@PathVariable("trip_id") int tripsId,
      @PathVariable("user_id") int userId) {

    String passengerStatus = service.getPassengerStatus(tripsId, userId);
    if (passengerStatus == null)
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(passengerStatus, HttpStatus.OK);
  }

  @PutMapping("/passengers/{trip_id}/{user_id}")
  public ResponseEntity<String> updatePassengerStatus(@PathVariable("trip_id") int tripsId,
      @PathVariable("user_id") int userId, @RequestParam("status") String status) {

    if (service.updatePassengerStatus(tripsId, userId, status))
      return new ResponseEntity<>(HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping("/passengers/user/{user_id}")
  public ResponseEntity<PassengerTrips> getPassengerTrips(@PathVariable("user_id") int userId) {
    return new ResponseEntity<>(service.getPassengerTrips(userId), HttpStatus.OK);
  }

  @DeleteMapping("/passengers/user/{user_id}")
  public ResponseEntity<String> removeAllParticipation(@PathVariable("user_id") int userId) {
    service.removeAllParticipation(userId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/passengers/trip/{trip_id}")
  public ResponseEntity<Passengers> getTripPassengers(@PathVariable("trip_id") int tripId) {
    return new ResponseEntity<>(service.getTripPassengers(tripId), HttpStatus.OK);
  }

  @DeleteMapping("/passengers/trip/{trip_id}")
  public ResponseEntity<String> removeAllPassenger(@PathVariable("trip_id") int tripId) {
    service.removeAllPassenger(tripId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
