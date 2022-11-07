package be.vinci.ipl.chattycar.passengers.data;

import be.vinci.ipl.chattycar.passengers.models.Trip;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "trips")
public interface TripsProxy {
  @GetMapping("/trips/{trip_id}")
  Trip readTrip(@PathVariable int id);
}
