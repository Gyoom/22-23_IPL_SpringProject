package com.example.mock_trips;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
  private int id;
  private Position origin;
  private Position destination;
  private LocalDate departure;
  private int driverId;
  private int available_seating;
}
