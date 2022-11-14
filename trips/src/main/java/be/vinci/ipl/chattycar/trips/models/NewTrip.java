package be.vinci.ipl.chattycar.trips.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NewTrip {
  private Position origin;
  private Position destination;

  private String departure;
  private int driver_id;
  private int available_seat;

  private static int index = 0;

  public Trip toTrip() {
    Trip newTrip = new Trip(index, origin, destination, departure, driver_id,available_seat);
    index++;
    return newTrip;
  }

}
