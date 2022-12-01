package be.vinci.ipl.chattycar.gateway;

import be.vinci.ipl.chattycar.gateway.data.*;
import be.vinci.ipl.chattycar.gateway.models.*;
import be.vinci.ipl.chattycar.gateway.models.Credentials;
import be.vinci.ipl.chattycar.gateway.models.UserWithCredentials;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public UserWithId createUser(UserWithCredentials user) {
        UserWithId newUser = usersProxy.createUser(user.toUser()); // throw 409 if the email already exists
        authenticationProxy.createCredentials(user.getEmail(), user.toCredentials());
        return newUser;
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

    public void deleteUser(UserWithId user) {
        // We can not create transaction, so we delete from less important to most important
        notificationProxy.deleteNotification(user.getId());
        passengersProxy.removeAllParticipation(user.getId());
        tripsProxy.deleteAllByDriver(user.getId());
        authenticationProxy.deleteCredentials(user.getEmail());
        usersProxy.deleteUser(user.getId());
    }

    public Iterable<Trip> getTripsOfDriver(int idDriver) {
        return tripsProxy.readOneByDriver(idDriver);
    }

    public PassengerTrips getTripsOfUser(int idUser) {
        return passengersProxy.getPassengerTrips(idUser);
    }

    public Trip createTrip(NewTrip trip){
        return tripsProxy.createOne(trip).getBody();
    }

    public ResponseEntity<Trip> readOne(int id){ return tripsProxy.readOne(id);}

    public ResponseEntity<Trip> deleteOne(int id){ return tripsProxy.deleteOne(id);}

}
