package be.vinci.ipl.chattycar.passengers;

import be.vinci.ipl.chattycar.passengers.models.PassengerTrips;
import be.vinci.ipl.chattycar.passengers.models.Passengers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    return new ResponseEntity<>(service.createPassenger(tripsId, userId));
  }

  @GetMapping("/passengers/{trip_id}/{user_id}")
  public ResponseEntity<String> getPassengerStatus(@PathVariable("trip_id") int tripsId,
      @PathVariable("user_id") int userId) {
    String passengerStatus = service.getPassengerStatus(tripsId, userId);
    if (passengerStatus == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(passengerStatus, HttpStatus.OK);
  }

  @PutMapping("/passengers/{trip_id}/{user_id}")
  public ResponseEntity<String> updatePassengerStatus(@PathVariable("trip_id") int tripsId,
      @PathVariable("user_id") int userId, @RequestParam("status") String status) {

    return new ResponseEntity<>(service.updatePassengerStatus(tripsId, userId, status));
  }

  @GetMapping("/passengers/user/{user_id}")
  public ResponseEntity<PassengerTrips> getPassengerTrips(@PathVariable("user_id") int userId) {

    PassengerTrips passengerTrips = service.getPassengerTrips(userId);
    if (passengerTrips == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(passengerTrips, HttpStatus.OK);
  }

  @GetMapping("/passengers/trip/{trip_id}")
  public ResponseEntity<Passengers> getTripPassengers(@PathVariable("trip_id") int tripId) {
    Passengers passengerTrips = service.getTripPassengers(tripId);
    if (passengerTrips == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(passengerTrips, HttpStatus.OK);
  }
}
