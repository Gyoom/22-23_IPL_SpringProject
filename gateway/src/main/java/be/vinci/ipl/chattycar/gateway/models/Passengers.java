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
public class Passengers {
  private List<User> pending;
  private List<User> accepted;
  private List<User> refused;
}
