package be.vinci.ipl.chattycar.passengers.models;

import java.util.List;

public class PassengerTrips {
  private List<Trip> pending;
  private List<Trip> accepted;
  private List<Trip> refused;

  public List<Trip> getPending() {
    return pending;
  }

  public List<Trip> getAccepted() {
    return accepted;
  }

  public List<Trip> getRefused() {
    return refused;
  }
}
