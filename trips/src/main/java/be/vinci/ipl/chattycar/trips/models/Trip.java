package be.vinci.ipl.chattycar.trips.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "trips")
public class Trip {
  @Id
  private int id;
  private Position origin;
  private Position destination;
  private String departure;
  private int driver_id;
  private int available_seat;

}
