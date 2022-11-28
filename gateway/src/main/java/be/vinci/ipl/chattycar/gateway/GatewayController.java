package be.vinci.ipl.chattycar.gateway;

import be.vinci.ipl.chattycar.gateway.models.*;
import be.vinci.ipl.chattycar.gateway.models.Credentials;
import be.vinci.ipl.chattycar.gateway.models.NoIdReview;
import be.vinci.ipl.chattycar.gateway.models.Review;
import be.vinci.ipl.chattycar.gateway.models.UserWithCredentials;
import be.vinci.ipl.chattycar.gateway.models.Video;
import be.vinci.ipl.chattycar.gateway.models.Trip;
import be.vinci.ipl.chattycar.gateway.models.Position;
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
    ResponseEntity<Void> createUser(@RequestBody UserWithCredentials user) {
        if (user.getEmail() == null || user.getFirstname() == null || user.getLastname() == null || user.getPassword() == null ) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        service.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users")
    UserWithId readUser(@RequestParam(value = "email") String email) {
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
    UserWithId getUser(@PathVariable int id, @RequestHeader("Authorization") String token) {
        service.verify(token);
        return service.getUser(id);
    }

    @PutMapping("/users/{id}")
    void updateUser(@PathVariable int id, @RequestBody UserWithId user, @RequestHeader("Authorization") String token) {
        if (user.getId() != id || user.getEmail() == null || user.getFirstname() == null || user.getLastname() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        String userEmail = service.verify(token);
        if (!userEmail.equals(user.getEmail())) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        service.updateUser(user);
    }

    @DeleteMapping("/users/{pseudo}")
    void deleteUser(@PathVariable String pseudo, @RequestHeader("Authorization") String token) {
        String user = service.verify(token);
        if (!user.equals(pseudo)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        service.deleteUser(pseudo);
    }

    @GetMapping("/users/{id_driver}/driver")
    public Iterable<Trip> getTripsOfDriver(@PathVariable("id_driver") int idDriver,
        @RequestHeader("Authorization") String token) {

        String email = service.verify(token);
        UserWithId user = service.getUser(idDriver);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!user.getEmail().equals(email)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return service.getTripsOfDriver(idDriver);
    }

    @GetMapping("/users/{id_user}/passenger")
    public PassengerTrips getTripsOfUser(@PathVariable("id_user") int idUser,
        @RequestHeader("Authorization") String token) {

        String email = service.verify(token);
        UserWithId user = service.getUser(idUser);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!user.getEmail().equals(email)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return service.getTripsOfUser(idUser);
    }

    @GetMapping("/users/{id_user}/notifications")
    public PassengerTrips getUserNotification(@PathVariable("id_user") int idUser,
        @RequestHeader("Authorization") String token) {

        String email = service.verify(token);
        UserWithId user = service.getUser(idUser);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!user.getEmail().equals(email)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return service.getUserNotification(idUser);
    }

    @DeleteMapping("/users/{id_user}/notifications")
    public PassengerTrips deleteAllUserNotification(@PathVariable("id_user") int idUser,
        @RequestHeader("Authorization") String token) {

        String email = service.verify(token);
        UserWithId user = service.getUser(idUser);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!user.getEmail().equals(email)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return service.deleteAllUserNotification(idUser);
    }



    @GetMapping("/users/{pseudo}/videos")
    Iterable<Video> readUserVideos(@PathVariable String pseudo) {
        return service.readVideosFromUser(pseudo);
    }

    @GetMapping("/users/{pseudo}/reviews")
    Iterable<Review> readUserReviews(@PathVariable String pseudo) {
        return service.readReviewsFromUser(pseudo);
    }

    @PostMapping("/trips")
    Trip createTrip(@RequestBody NewTrip trip, @RequestHeader("Authorization") String token){
        String userEmail = service.verify(token);
        UserWithId user = service.readUser(userEmail);

        if (user.getId() != trip.getDriver_id()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Trip createdTrip = service.createTrip(trip);

        return createdTrip;

    }

    @GetMapping("/trips")
    ResponseEntity<Trip> readAll(){
        //TODO ajouter arguments optionnels
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/trips/{id}")
    ResponseEntity<Trip> readOne(@PathVariable int id){
        return service.readOne(id);
    }

    @DeleteMapping("/trips/{id}")
    ResponseEntity<Trip> deleteOne(@PathVariable int id, @RequestHeader("Authorization") String token){
        String userEmail = service.verify(token);
        UserWithId user = service.readUser(userEmail);

        Trip trip = service.readOne(id).getBody();

        if (trip.getDriverId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return service.deleteOne(id);
    }

}
