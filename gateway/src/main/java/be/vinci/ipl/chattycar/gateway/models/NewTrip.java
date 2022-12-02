package be.vinci.ipl.chattycar.gateway.models;

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
  private int driverId;
  private int availableSeat;

}
