package be.vinci.ipl.chattycar.passengers.data;

import org.springframework.http.ResponseEntity;
import be.vinci.ipl.chattycar.passengers.models.NoIdTrip;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "trips")
public interface TripsProxy {
  @GetMapping("/trips/{id}")
  ResponseEntity<NoIdTrip> readOne(@PathVariable int id);
}
