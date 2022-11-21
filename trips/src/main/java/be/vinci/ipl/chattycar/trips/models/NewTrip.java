package be.vinci.ipl.chattycar.trips.models;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NewTrip {
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "latitude", column = @Column(name = "origin_latitude")),
      @AttributeOverride(name = "longitude", column = @Column(name = "origin_longitude")),
  })
  private Position origin;
  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "latitude", column = @Column(name = "destination_latitude")),
      @AttributeOverride(name = "longitude", column = @Column(name = "destination_longitude")),
  })
  private Position destination;

  private String departure;
  private int driver_id;
  private int available_seat;

  private static int index = 1;

  public Trip toTrip() {
    Trip newTrip = new Trip(index, origin, destination, departure, driver_id,available_seat);
    index++;
    return newTrip;
  }

}
