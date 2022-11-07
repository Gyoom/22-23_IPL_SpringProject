package be.vinci.ipl.chattycar.trips;

import be.vinci.ipl.chattycar.trips.models.NewTrip;
import be.vinci.ipl.chattycar.trips.models.Trip;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TripsController {

  private final TripsService service;

  public TripsController(TripsService service) {
    this.service = service;
  }

  @PostMapping("/trips")
  public ResponseEntity<Void> createOne(@RequestBody NewTrip newTrip) {
    if (newTrip.getOrigin() == null || newTrip.getDestination() == null ||
        newTrip.getDeparture() == null || newTrip.getDriver_id() <= 0 ||
        newTrip.getAvailable_seat() < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    boolean created = service.createOne(newTrip);
    if (!created) throw new ResponseStatusException(HttpStatus.CONFLICT);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping("/trips")
  public Iterable<Trip> readAll() {;
    Iterable<Trip> trips = service.readAll();

    if (trips == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    return trips;
  }

  @GetMapping("/trips/{id}")
  public Trip readOne(@PathVariable int id) {
    Trip trip = service.readOne(id);
    if (trip == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    return trip;
  }

  @DeleteMapping("/trips/{id}")
  public void deleteOne(@PathVariable int id) {
    boolean found = service.deleteOne(id);
    if (!found) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/trips/driver/{id}")
  public Iterable<Trip> readOneByDriver(@PathVariable int id) {
    return service.readOneByDriver(id);
  }


}
