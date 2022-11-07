package be.vinci.ipl.chattycar.passengers.models;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PassengerTrips {
  private List<Trip> pending;
  private List<Trip> accepted;
  private List<Trip> refused;
}
