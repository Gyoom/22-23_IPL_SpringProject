package be.vinci.ipl.chattycar.passengers.models;

import java.util.List;

public class Passengers {
  private List<User> pending;
  private List<User> accepted;
  private List<User> refused;

  public List<User> getPending() {
    return pending;
  }

  public List<User> getAccepted() {
    return accepted;
  }

  public List<User> getRefused() {
    return refused;
  }
}
