package be.vinci.ipl.chattycar.passengers.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Passenger {
  @Id
  @Column(name = "passenger_id")
  private int passengerId;
  @Column(name = "user_id")
  private int userId;
  @Column(name = "trip_id")
  private int tripId;
  private String status;

  public int getPassengerId() {
    return passengerId;
  }

  public void setPassengerId(int passengerId) {
    this.passengerId = passengerId;
  }

  public int getUserId() {
    return userId;
  }

  public int getTripId() {
    return tripId;
  }

  public String getStatus() {
    return status;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public void setTripId(int tripId) {
    this.tripId = tripId;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
