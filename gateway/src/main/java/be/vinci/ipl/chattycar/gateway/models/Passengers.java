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
  private List<UserWithoutId> pending;
  private List<UserWithoutId> accepted;
  private List<UserWithoutId> refused;
}
