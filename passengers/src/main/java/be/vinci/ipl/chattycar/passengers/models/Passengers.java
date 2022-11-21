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
public class Passengers {
  private List<NoIdUser> pending;
  private List<NoIdUser> accepted;
  private List<NoIdUser> refused;
}
