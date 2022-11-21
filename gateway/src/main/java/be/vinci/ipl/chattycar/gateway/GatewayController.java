package be.vinci.ipl.chattycar.gateway;

import be.vinci.ipl.chattycar.gateway.models.*;
import be.vinci.ipl.chattycar.gateway.models.Credentials;
import be.vinci.ipl.chattycar.gateway.models.NoIdReview;
import be.vinci.ipl.chattycar.gateway.models.Review;
import be.vinci.ipl.chattycar.gateway.models.User;
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


    @PostMapping("/users/{pseudo}")
    ResponseEntity<Void> createUser(@PathVariable String pseudo, @RequestBody UserWithCredentials user) {
        if (!user.getPseudo().equals(pseudo)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        service.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users/{pseudo}")
    User readUser(@PathVariable String pseudo) {
        return service.readUser(pseudo);
    }

    @PutMapping("/users/{pseudo}")
    void updateUser(@PathVariable String pseudo, @RequestBody UserWithCredentials user, @RequestHeader("Authorization") String token) {
        if (!user.getPseudo().equals(pseudo)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        String userPseudo = service.verify(token);
        if (!userPseudo.equals(pseudo)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        service.updateUser(user);
    }

    @DeleteMapping("/users/{pseudo}")
    void deleteUser(@PathVariable String pseudo, @RequestHeader("Authorization") String token) {
        String user = service.verify(token);
        if (!user.equals(pseudo)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        service.deleteUser(pseudo);
    }


    @GetMapping("/users/{pseudo}/videos")
    Iterable<Video> readUserVideos(@PathVariable String pseudo) {
        return service.readVideosFromUser(pseudo);
    }

    @GetMapping("/users/{pseudo}/reviews")
    Iterable<Review> readUserReviews(@PathVariable String pseudo) {
        return service.readReviewsFromUser(pseudo);
    }


    @GetMapping("/videos")
    Iterable<Video> readVideos() {
        return service.readVideos();
    }


    @GetMapping("/videos/best")
    Iterable<Video> readBestVideos() {
        return service.readBestVideos();
    }


    @PostMapping("/videos/{hash}")
    ResponseEntity<Void> createVideo(@PathVariable String hash, @RequestBody Video video, @RequestHeader("Authorization") String token) {
        if (!video.getHash().equals(hash)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        String user = service.verify(token);
        if (!video.getAuthor().equals(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        service.createVideo(video);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/videos/{hash}")
    Video readVideo(@PathVariable String hash) {
        return service.readVideo(hash);
    }

    @PutMapping("/videos/{hash}")
    void updateVideo(@PathVariable String hash, @RequestBody Video video, @RequestHeader("Authorization") String token) {
        if (!video.getHash().equals(hash)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        String user = service.verify(token);
        if (!video.getAuthor().equals(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        service.updateVideo(video);
    }

    @DeleteMapping("/videos/{hash}")
    void deleteVideo(@PathVariable String hash, @RequestHeader("Authorization") String token) {
        String user = service.verify(token);
        Video video = service.readVideo(hash);
        if (!video.getAuthor().equals(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        service.deleteVideo(hash);
    }


    @GetMapping("/videos/{hash}/reviews")
    Iterable<Review> readVideoReviews(@PathVariable String hash) {
        return service.readReviewsOfVideo(hash);
    }


    @PostMapping("/reviews")
    ResponseEntity<Review> createReview(@RequestBody NoIdReview review, @RequestHeader("Authorization") String token) {
        String user = service.verify(token);
        if (!review.getPseudo().equals(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        Review createdReview = service.createReview(review);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/reviews/{id}")
    Review readReview(@PathVariable long id) {
        return service.readReview(id);
    }

    @PutMapping("/reviews/{id}")
    void updateReview(@PathVariable long id, @RequestBody Review review, @RequestHeader("Authorization") String token) {
        if (review.getId() != id) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        String user = service.verify(token);
        if (!review.getPseudo().equals(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        service.updateReview(review);
    }

    @DeleteMapping("/reviews/{id}")
    void deleteReview(@PathVariable long id, @RequestHeader("Authorization") String token) {
        String user = service.verify(token);
        Review review = service.readReview(id);
        if (!review.getPseudo().equals(user)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        service.deleteReview(id);
    }

    @PostMapping("/trips")
    Trip createTrip(@RequestBody NewTrip trip, @RequestHeader("Authorization") String token){
        String user = service.verify(token);

        Trip createdTrip = service.createTrip(trip);

        return createdTrip;

    }

}
