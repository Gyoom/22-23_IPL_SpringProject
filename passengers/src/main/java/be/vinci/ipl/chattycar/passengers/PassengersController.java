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

  @PostMapping("/passengers/{tripsId}/{userId}")
  public ResponseEntity<String> createPassenger(@PathVariable int tripsId, @PathVariable int userId) {
    return new ResponseEntity<>(service.createPassenger(tripsId, userId));
  }

  @GetMapping("/passengers/{tripsId}/{userId}")
  public ResponseEntity<String> getPassengerStatus(@PathVariable int tripsId, @PathVariable int userId) {
    String passengerStatus = service.getPassengerStatus(tripsId, userId);
    if (passengerStatus == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(passengerStatus, HttpStatus.ACCEPTED);
  }

  @PutMapping("/passengers/{tripsId}/{userId}")
  public ResponseEntity<String> updatePassengerStatus(@PathVariable int tripsId, @PathVariable int userId,
      @RequestParam("status") String status) {

    return new ResponseEntity<>(service.updatePassengerStatus(tripsId, userId, status));
  }

  @GetMapping("/passengers/{userId}")
  public ResponseEntity<PassengerTrips> getPassengerTrips(@PathVariable int userId) {

    PassengerTrips passengerTrips = service.getPassengerTrips(userId);
    if (passengerTrips == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(passengerTrips, HttpStatus.ACCEPTED);
  }

  @GetMapping("/passengers/{tripId}")
  public ResponseEntity<Passengers> getTripPassengers(@PathVariable int tripId) {
    Passengers passengerTrips = service.getTripPassengers(tripId);
    if (passengerTrips == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(passengerTrips, HttpStatus.ACCEPTED);
  }
}
