package be.vinci.ipl.chattycar.gateway.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Position {
  private double longitude;
  private double latitude;
}
