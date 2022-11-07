package be.vinci.ipl.chattycar.passengers.models;

import java.time.LocalDate;

public class Trip {
  private int id;
  private Position origin;
  private Position destination;
  private LocalDate departure;
  private int driverId;
  private int available_seating;

  public int getId() {
    return id;
  }

  public Position getOrigin() {
    return origin;
  }

  public Position getDestination() {
    return destination;
  }

  public LocalDate getDeparture() {
    return departure;
  }

  public int getDriverId() {
    return driverId;
  }

  public int getAvailable_seating() {
    return available_seating;
  }
}
