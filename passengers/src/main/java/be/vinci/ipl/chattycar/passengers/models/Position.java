package be.vinci.ipl.chattycar.passengers.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Position {
  private double longitude;
  private double latitude;
}
