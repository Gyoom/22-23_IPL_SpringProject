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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TripsController {

  private final TripsService service;

  public TripsController(TripsService service) {
    this.service = service;
  }

  @PostMapping("/trips")
  public ResponseEntity<Trip> createOne(@RequestBody NewTrip newTrip) {
    if (newTrip.getOrigin() == null || newTrip.getDestination() == null ||
        newTrip.getDeparture() == null || newTrip.getDriver_id() <= 0 ||
        newTrip.getAvailable_seat() < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip in request is not correct");
    }
    Trip created = service.createOne(newTrip);
    if (created == null) throw new ResponseStatusException(HttpStatus.CONFLICT, "Trip already exist");
    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }

  @GetMapping("/trips")
  public ResponseEntity<Iterable<Trip>> readAll() {
    Iterable<Trip> trips = service.readAll();
    return new ResponseEntity<>(trips, HttpStatus.OK);
  }

  @GetMapping(value = "/trips", params = { "departure_date"})
  public ResponseEntity<Iterable<Trip>> readAllWithDepartureDate(
      @RequestParam String departure_date) {
    if (departure_date == "")  throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST, "departure date in request is not correct");
    Iterable<Trip> trips = service.readWithDepartureDate(departure_date);
    return new ResponseEntity<>(trips, HttpStatus.OK);
  }

  @GetMapping(value = "/trips", params = { "originLat", "originLon"})
  public ResponseEntity<Iterable<Trip>> readAllWithOrigin(
      @RequestParam float originLat,
      @RequestParam float originLon) {
    Iterable<Trip> trips = service.readWithOrigin(originLat, originLon);
    return new ResponseEntity<>(trips, HttpStatus.OK);
  }

  @GetMapping(value = "/trips", params = { "destinationLat", "destinationLon"})
  public ResponseEntity<Iterable<Trip>> readAllWithDestination(
      @RequestParam float destinationLat,
      @RequestParam float destinationLon) {
    Iterable<Trip> trips = service.readWithDestination(destinationLat, destinationLon);
    return new ResponseEntity<>(trips, HttpStatus.OK);
  }

  @GetMapping("/trips/{id}")
  public ResponseEntity<Trip> readOne(@PathVariable int id) {
    Trip trip = service.readOne(id);
    if (trip == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No trip found with this ID");
    return new ResponseEntity(trip, HttpStatus.OK);
  }

  @DeleteMapping("/trips/{id}")
  public ResponseEntity<Trip> deleteOne(@PathVariable int id) {
    if (id <= 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip in request is not correct");
    }
    Trip trip = service.deleteOne(id);
    if (trip == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    return new ResponseEntity(trip, HttpStatus.OK);
  }

  @GetMapping("/trips/driver/{id}")
  public ResponseEntity<Iterable<Trip>> readAllThoseDriver(@PathVariable int id) {
    if (id <= 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Driver id must be positive");
    }
    Iterable<Trip> trips = service.readAllThoseDriver(id);
    return new ResponseEntity(trips, HttpStatus.OK);
  }

  @GetMapping(value = "/trips/driver/{id}", params = { "departure_date"})
  public ResponseEntity<Iterable<Trip>> readAllThoseDriverWithDepartureDate(
      @PathVariable int id,
      @RequestParam String departure_date) {
    if (id <= 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Driver id must be positive");
    }
    if (departure_date == "")  throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST, "departure date in request is not correct");
    Iterable<Trip> trips = service.readAllFromDriverWithDepartureDate(id, departure_date);
    return new ResponseEntity<>(trips, HttpStatus.OK);
  }

  @GetMapping(value = "/trips/driver/{id}", params = { "originLat", "originLon"})
  public ResponseEntity<Iterable<Trip>> readAllThoseDriverWithOrigin(
      @PathVariable int id,
      @RequestParam float originLat,
      @RequestParam float originLon) {
    if (id <= 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Driver id must be positive");
    }
    Iterable<Trip> trips = service.readAllThoseDriverWithOrigin(id, originLat, originLon);
    return new ResponseEntity<>(trips, HttpStatus.OK);
  }

  @GetMapping(value = "/trips/driver/{id}", params = { "destinationLat", "destinationLon"})
  public ResponseEntity<Iterable<Trip>> readAllThoseDriverWithDestination(
      @PathVariable int id,
      @RequestParam float destinationLat,
      @RequestParam float destinationLon) {
    if (id <= 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Driver id must be positive");
    }
    Iterable<Trip> trips = service.readAllThoseDriverWithDestination(id, destinationLat, destinationLon);
    return new ResponseEntity<>(trips, HttpStatus.OK);
  }

  @DeleteMapping("/trips/driver/{id}")
  public ResponseEntity<Iterable<Trip>> deleteAllByDriver(@PathVariable int id) {
    if (id <= 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip in request is not correct");
    }
    Iterable<Trip> trips = service.deleteAllByDriver(id);
    return new ResponseEntity(trips, HttpStatus.OK);

  }


}
