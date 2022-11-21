package be.vinci.ipl.chattycar.passengers.models;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class NoIdTrip {
  private Position origin;
  private Position destination;
  private LocalDate departure;
  private int driverId;
  private int available_seating;
}
