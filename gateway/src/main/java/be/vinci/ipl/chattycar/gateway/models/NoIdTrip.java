package be.vinci.ipl.chattycar.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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
  @JsonProperty("driver_id")
  private int driverId;
  @JsonProperty("available_seating")
  private int availableSeating;
}
