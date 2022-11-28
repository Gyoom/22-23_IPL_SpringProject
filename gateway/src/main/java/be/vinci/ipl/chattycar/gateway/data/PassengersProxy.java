package be.vinci.ipl.chattycar.gateway.data;

import be.vinci.ipl.chattycar.gateway.models.PassengerTrips;
import be.vinci.ipl.chattycar.gateway.models.Passengers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface PassengersProxy {

  @PostMapping("/passengers/{trip_id}/{user_id}")
  ResponseEntity<Void> createPassenger(@PathVariable("trip_id") int tripsId,
      @PathVariable("user_id") int userId);

  @GetMapping("/passengers/{trip_id}/{user_id}")
  String getPassengerStatus(@PathVariable("trip_id") int tripsId,
      @PathVariable("user_id") int userId);

  @PutMapping("/passengers/{trip_id}/{user_id}")
  void updatePassengerStatus(@PathVariable("trip_id") int tripsId,
      @PathVariable("user_id") int userId, @RequestParam("status") String status);

  @GetMapping("/passengers/user/{user_id}")
  PassengerTrips getPassengerTrips(@PathVariable("user_id") int userId);

  @DeleteMapping("/passengers/user/{user_id}")
  void removeAllParticipation(@PathVariable("user_id") int userId);

  @GetMapping("/passengers/trip/{trip_id}")
  Passengers getTripPassengers(@PathVariable("trip_id") int tripId);

  @DeleteMapping("/passengers/trip/{trip_id}")
  void removeAllPassenger(@PathVariable("trip_id") int tripId);
}
