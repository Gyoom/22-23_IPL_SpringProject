package be.vinci.ipl.chattycar.gateway;

import be.vinci.ipl.chattycar.gateway.data.*;
import be.vinci.ipl.chattycar.gateway.models.*;
import be.vinci.ipl.chattycar.gateway.models.Credentials;
import be.vinci.ipl.chattycar.gateway.models.NoIdReview;
import be.vinci.ipl.chattycar.gateway.models.Review;
import be.vinci.ipl.chattycar.gateway.models.UserWithCredentials;
import be.vinci.ipl.chattycar.gateway.models.Video;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {

    private final AuthenticationProxy authenticationProxy;
    private final ReviewsProxy reviewsProxy;
    private final UsersProxy usersProxy;
    private final VideosProxy videosProxy;
    private final TripsProxy tripsProxy;
    private final PassengersProxy passengersProxy;

    public GatewayService(AuthenticationProxy authenticationProxy,
                          ReviewsProxy reviewsProxy,
                          UsersProxy usersProxy,
                          VideosProxy videosProxy,
                          TripsProxy tripsProxy,
                          PassengersProxy passengersProxy) {
        this.authenticationProxy = authenticationProxy;
        this.reviewsProxy = reviewsProxy;
        this.usersProxy = usersProxy;
        this.videosProxy = videosProxy;
        this.tripsProxy = tripsProxy;
        this.passengersProxy = passengersProxy;
    }

    public String connect(Credentials credentials) {
        return authenticationProxy.connect(credentials);
    }

    public String verify(String token) {
        return authenticationProxy.verify(token);
    }

    public void createUser(UserWithCredentials user) {
        usersProxy.createUser(user.getEmail(), user.toUser()); // throw 409 if the email already exists
        authenticationProxy.createCredentials(user.getEmail(), user.toCredentials());
    }

    public UserWithId readUser(String email) {
        return usersProxy.readUser(email);
    }

    public void updateUserPassword(Credentials credentials) {
        authenticationProxy.updateOne(credentials.getEmail(), credentials);
    }

    public UserWithId getUser(int id) {
        return usersProxy.getOne(id);
    }

    public void updateUser(UserWithId user) {
        usersProxy.updateUser(user.getId(), user);
    }

    public void deleteUser(String pseudo) {
        reviewsProxy.deleteReviewsFromUser(pseudo);
        videosProxy.deleteVideosFromAuthor(pseudo);
        authenticationProxy.deleteCredentials(pseudo);
        usersProxy.deleteUser(pseudo);
    }

    public Iterable<Video> readVideos() {
        return videosProxy.readVideos();
    }

    public void createVideo(Video video) {
        usersProxy.readUser(video.getAuthor());
        videosProxy.createVideo(video.getHash(), video);
    }

    public Video readVideo(String hash) {
        return videosProxy.readVideo(hash);
    }

    public void updateVideo(Video video) {
        videosProxy.updateVideo(video.getHash(), video);
    }

    public void deleteVideo(String hash) {
        reviewsProxy.deleteReviewsOfVideo(hash);
        videosProxy.deleteVideo(hash);
    }

    public Iterable<Video> readVideosFromUser(String pseudo) {
        usersProxy.readUser(pseudo);
        return videosProxy.readVideosFromAuthor(pseudo);
    }

    public Review createReview(NoIdReview review) {
        usersProxy.readUser(review.getPseudo());
        videosProxy.readVideo(review.getHash());
        return reviewsProxy.createReview(review);
    }

    public Review readReview(long id) {
        return reviewsProxy.readReview(id);
    }

    public void updateReview(Review review) {
        reviewsProxy.updateReview(review.getId(), review);
    }

    public void deleteReview(long id) {
        reviewsProxy.deleteReview(id);
    }

    public Iterable<Review> readReviewsFromUser(String pseudo) {
        usersProxy.readUser(pseudo);
        return reviewsProxy.readReviewsFromUser(pseudo);
    }

    public Iterable<Review> readReviewsOfVideo(String hash) {
        videosProxy.readVideo(hash);
        return reviewsProxy.readReviewsOfVideo(hash);
    }

    public Iterable<Video> readBestVideos() {
        return reviewsProxy.readBestVideos();
    }

    public Trip createTrip(NewTrip trip){
        return tripsProxy.createOne(trip).getBody();
    }

    public ResponseEntity<Trip> readOne(int id){ return tripsProxy.readOne(id);}

    public ResponseEntity<Trip> deleteOne(int id){ return tripsProxy.deleteOne(id);}

    public Passengers getTripPassengers(int id){return passengersProxy.getTripPassengers(id);}

}
