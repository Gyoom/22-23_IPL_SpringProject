package be.vinci.ipl.chattycar.gateway;

import be.vinci.ipl.chattycar.gateway.models.*;
import be.vinci.ipl.chattycar.gateway.models.Credentials;
import be.vinci.ipl.chattycar.gateway.models.UserWithCredentials;
import be.vinci.ipl.chattycar.gateway.models.Trip;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = { "https://www.catflix.com", "http://localhost" })
@RestController
public class GatewayController {

    private final GatewayService service;

    public GatewayController(GatewayService service) {
        this.service = service;
    }


    @PostMapping("/auth")
    String connect(@RequestBody Credentials credentials) {
        return service.connect(credentials);
    }


    @PostMapping("/users")
    ResponseEntity<User> createUser(@RequestBody UserWithCredentials user) {
        if (user.getEmail() == null || user.getFirstname() == null || user.getLastname() == null || user.getPassword() == null ) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        User newUser = service.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    User readUser(@RequestParam String email) {
        return service.readUser(email);
    }

    @PutMapping("/users")
    void updateUserPassword(@RequestBody Credentials credentials, @RequestHeader("Authorization") String token) {
        if (credentials.getEmail() == null || credentials.getPassword() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        String userEmail = service.verify(token);
        if (!userEmail.equals(credentials.getEmail())) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        service.updateUserPassword(credentials);
    }

    @GetMapping("/users/{id}")
    User getUser(@PathVariable int id, @RequestHeader("Authorization") String token) {
        service.verify(token);
        return service.getUser(id);
    }

    @PutMapping("/users/{id}")
    void updateUser(@PathVariable int id, @RequestBody User user, @RequestHeader("Authorization") String token) {
        if (user.getId() != id || user.getEmail() == null || user.getFirstname() == null || user.getLastname() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        String userEmail = service.verify(token);
        if (!userEmail.equals(user.getEmail())) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        service.updateUser(user);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable int id, @RequestHeader("Authorization") String token) {
        String userEmail = service.verify(token);
        User user = service.getUser(id);
        if (!userEmail.equals(user.getEmail())) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        service.deleteUser(user);
    }

    @GetMapping("/users/{id_driver}/driver")
    public Iterable<Trip> getTripsOfDriver(@PathVariable("id_driver") int idDriver,
        @RequestHeader("Authorization") String token) {

        isAuthorized(token, idDriver);
        return service.getTripsOfDriver(idDriver);
    }

    @GetMapping("/users/{id_user}/passenger")
    public PassengerTrips getTripsOfUser(@PathVariable("id_user") int idUser,
        @RequestHeader("Authorization") String token) {

        isAuthorized(token, idUser);
        return service.getTripsOfUser(idUser);
    }

    @GetMapping("/users/{id_user}/notifications")
    public ResponseEntity<List<Notification>> getUserNotification(@PathVariable("id_user") int idUser,
        @RequestHeader("Authorization") String token) {

        isAuthorized(token, idUser);
        return new ResponseEntity<>(service.getUserNotification(idUser), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id_user}/notifications")
    public void deleteAllUserNotification(@PathVariable("id_user") int idUser,
        @RequestHeader("Authorization") String token) {

        isAuthorized(token, idUser);
        service.deleteAllUserNotification(idUser);
    }

    private void isAuthorized(String token, int idUser) {
        if (token == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        String email = service.verify(token);
        User user = service.getUser(idUser);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!user.getEmail().equals(email)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/trips")
    Trip createTrip(@RequestBody NewTrip trip, @RequestHeader("Authorization") String token){
        String userEmail = service.verify(token);
        User user = service.readUser(userEmail);

        if (user.getId() != trip.getDriverId()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Trip createdTrip = service.createTrip(trip);

        return createdTrip;

    }

    @GetMapping("/trips")
    Iterable<Trip> readAll(
        @RequestParam(required = false) String departure_date,
        @RequestParam(required = false) Double origin_lat,
        @RequestParam(required = false) Double origin_lon,
        @RequestParam(required = false) Double destination_lat,
        @RequestParam(required = false) Double destination_lon){
        if ((origin_lon != null && origin_lat == null) || (origin_lon == null && origin_lat != null)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if ((destination_lon != null && destination_lat == null) || (destination_lon == null && destination_lat
            != null)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return service.readAll(departure_date, origin_lat, origin_lon, destination_lat, destination_lon);
    }

    @GetMapping("/trips/{id}")
    ResponseEntity<Trip> readOne(@PathVariable int id){
        return service.readOne(id);
    }

    @DeleteMapping("/trips/{id}")
    ResponseEntity<Trip> deleteOne(@PathVariable int id, @RequestHeader("Authorization") String token){
        String userEmail = service.verify(token);
        User user = service.readUser(userEmail);

        Trip trip = service.readOne(id).getBody();

        if (trip.getDriverId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return service.deleteOne(id);
    }

    @GetMapping("/trips/{id}/passengers")
    Passengers getTripsPassengers(@PathVariable int id, @RequestHeader("Authorization") String token){
        String userEmail = service.verify(token);
        User user = service.readUser(userEmail);

        Trip trip = service.readOne(id).getBody();

        if (trip == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (user.getId() != trip.getDriverId()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return service.getTripPassengers(id);
    }

    @PostMapping("/trips/{trip_id}/passengers/{user_id}")
    ResponseEntity<Void> addPendingPassengerInTrip(@PathVariable("trip_id") int tripsId, @PathVariable("user_id") int userId, @RequestHeader("Authorization") String token){
        String userEmail = service.verify(token);
        User user = service.readUser(userEmail);

        Trip trip = service.readOne(tripsId).getBody();

        if (trip == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (user.getId() != userId){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return service.createPassenger(tripsId, userId);
    }

    @GetMapping("/trips/{trips_id}/passengers/{user_id}")
    String getPassengerStatus(@PathVariable("trips_id") int tripsId, @PathVariable("user_id") int userId, @RequestHeader("Authorization") String token){
        String userEmail = service.verify(token);
        User user = service.readUser(userEmail);

        Trip trip = service.readOne(tripsId).getBody();

        if (trip == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (user.getId() != userId){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return service.getPassengerStatus(tripsId, userId);
    }


    @PutMapping("/trips/{trips_id}/passengers/{user_id}")
    void updatePassengerStatus(@PathVariable("trips_id") int tripsId, @PathVariable("user_id") int userId, @RequestHeader("Authorization") String token, @RequestBody String status){
        String userEmail = service.verify(token);
        User user = service.readUser(userEmail);

        if (user.getId() != userId){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        String oldStatus = service.getPassengerStatus(tripsId, userId);

        if (oldStatus == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        service.updatePassengerStatus(tripsId, userId, status);
    }

    @DeleteMapping("/trips/{trips_id}/passengers/{user_id}")
    void deletePassenger(@PathVariable("trips_id") int tripsId, @PathVariable("user_id") int userId, @RequestHeader("Authorization") String token){
        String userEmail = service.verify(token);
        User user = service.readUser(userEmail);

        if (user.getId() != userId){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        String oldStatus = service.getPassengerStatus(tripsId, userId);

        if (oldStatus == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (tripsId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        service.removeOneParticipation(userId, tripsId);


    }




}
