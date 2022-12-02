package be.vinci.ipl.chattycar.passengers.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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
  private String departure;
  @JsonProperty("driver_id")
  private int driverId;
  @JsonProperty("available_seating")
  private int availableSeat;
}
