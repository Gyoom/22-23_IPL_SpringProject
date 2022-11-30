package be.vinci.ipl.chattycar.gateway;

import be.vinci.ipl.chattycar.gateway.data.*;
import be.vinci.ipl.chattycar.gateway.models.*;
import be.vinci.ipl.chattycar.gateway.models.Credentials;
import be.vinci.ipl.chattycar.gateway.models.UserWithCredentials;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class GatewayService {

    private final AuthenticationProxy authenticationProxy;
    private final NotificationProxy notificationProxy;
    private final UsersProxy usersProxy;
    private final TripsProxy tripsProxy;
    private final PassengersProxy passengersProxy;

    public GatewayService(AuthenticationProxy authenticationProxy,
                          NotificationProxy notificationProxy,
                          UsersProxy usersProxy,
                          TripsProxy tripsProxy,
                          PassengersProxy passengersProxy) {

        this.authenticationProxy = authenticationProxy;
        this.notificationProxy = notificationProxy;
        this.usersProxy = usersProxy;
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

    public void deleteUser(int id) {
        passengersProxy.removeAllParticipation(id);
        usersProxy.deleteUser(id);
    }

    public Iterable<Trip> getTripsOfDriver(int idDriver) {
        return tripsProxy.readOneByDriver(idDriver);
    }

    public PassengerTrips getTripsOfUser(int idUser) {
        return passengersProxy.getPassengerTrips(idUser);
    }

    public List<Notification> getUserNotification(int idUser) {
        return notificationProxy.getNotifications(idUser);
    }

    public void deleteAllUserNotification(int idUser) {
        notificationProxy.deleteNotification(idUser);
    }

    public Trip createTrip(NewTrip trip){
        return tripsProxy.createOne(trip).getBody();
    }

    public ResponseEntity<Trip> readOne(int id){ return tripsProxy.readOne(id);}

    public ResponseEntity<Trip> deleteOne(int id){ return tripsProxy.deleteOne(id);}

    public Passengers getTripPassengers(int id){return passengersProxy.getTripPassengers(id);}

    public ResponseEntity<Void> createPassenger(int tripsId, int userId){return passengersProxy.createPassenger(tripsId, userId);}

    public String getPassengerStatus(int tripsId, int userId){return passengersProxy.getPassengerStatus(tripsId, userId);}

    public void updatePassengerStatus(int tripsId, int userId, String status){passengersProxy.updatePassengerStatus(tripsId, userId, status);};

    public void removeAllParticipation(int userId){passengersProxy.removeAllParticipation(userId);};
}
