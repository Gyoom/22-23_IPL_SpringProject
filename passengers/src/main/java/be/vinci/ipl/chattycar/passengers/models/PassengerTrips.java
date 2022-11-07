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

  public void setPending(List<Trip> pending) {
    this.pending = pending;
  }

  public void setAccepted(List<Trip> accepted) {
    this.accepted = accepted;
  }

  public void setRefused(List<Trip> refused) {
    this.refused = refused;
  }
}
