package be.vinci.ipl.chattycar.gateway.models;

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
  private List<NewTrip> pending;
  private List<NewTrip> accepted;
  private List<NewTrip> refused;
}
