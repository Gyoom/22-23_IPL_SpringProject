package be.vinci.ipl.chattycar.gateway.models;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
  private int id;
  private Position origin;
  private Position destination;
  private String departure;
  private int driverId;
  private int available_seating;
}
