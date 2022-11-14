package be.vinci.ipl.chattycar.trips.models;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Embeddable
public class Position {
  private int latitude;
  private int longitude;
}
