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
  public ResponseEntity<Iterable<Trip>> readAll(
      @RequestParam(required = false) String departure_date,
      @RequestParam(required = false) Double origin_lat,
      @RequestParam(required = false) Double originLon,
      @RequestParam(required = false) Double destinationLat,
      @RequestParam(required = false) Double destination_lon
  ) {
    // checks :
    CheckAll(
        departure_date,
        origin_lat,
        originLon,
        destinationLat,
        destination_lon
    );
    // call next method :
    System.out.println(destinationLat + " " + destination_lon);
    Iterable<Trip> trips = service.readAll(departure_date, origin_lat, originLon, destinationLat,
        destination_lon);
    return new ResponseEntity<>(trips, HttpStatus.OK);
  }

  @GetMapping("/trips/{id}")
  public ResponseEntity<Trip> readOne(@PathVariable int id) {
    System.out.println("trip controller" + id);
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
  public ResponseEntity<Iterable<Trip>> readAllThoseDriver(
      @PathVariable int id,
      String departure_date,
      Double originLat,
      Double originLon,
      Double destinationLat,
      Double destinationLon
  ) {
    // checks :
    if (id <= 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Driver id must be positive");
    }
    CheckAll(
        departure_date,
        originLat,
        originLon,
        destinationLat,
        destinationLon
    );
    // call next method :
    Iterable<Trip> trips = service.readAllThoseDriver(id, departure_date, originLat, originLon, destinationLat, destinationLon);
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

  /**
   *
   * @param departure_date : the date of the trip
   * @param originLat : the latitude of the trip origin
   * @param originLon : the longitude of the trip origin
   * @param destinationLat : the latitude of the trip destination
   * @param destinationLon : the longitude of the trip destination
   */
  private void CheckAll(
      String departure_date,
      Double originLat,
      Double originLon,
      Double destinationLat,
      Double destinationLon
  ) {
    // Departure checks :
    if (departure_date != null && departure_date == "")
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "departure date in request is not correct");
    // Origin checks :
    if ((originLat == null && originLon != null) || (originLat != null && originLon == null))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "originLat && originLon have to be initialized both");
    if (originLat != null && (originLat > 180 ||  originLat < -180))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "originLat in request is not correct");
    if (originLon != null && (originLon > 180 ||  originLon < -180))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "originLon in request is not correct");
    // Destination checks :
    if ((destinationLat == null && destinationLon != null) || (destinationLat != null && destinationLon == null))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "destinationLat && destinationLon have to be initialized both");
    if (destinationLat != null && (destinationLat > 180 ||  destinationLat < -180))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "destinationLat in request is not correct");
    if (destinationLon != null && (destinationLon > 180 ||  destinationLon < -180))
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "destinationLon in request is not correct");
  }




}
