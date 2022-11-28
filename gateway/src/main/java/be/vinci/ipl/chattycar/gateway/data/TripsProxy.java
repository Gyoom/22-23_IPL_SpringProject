package be.vinci.ipl.chattycar.gateway.data;

import be.vinci.ipl.chattycar.gateway.models.NewTrip;
import be.vinci.ipl.chattycar.gateway.models.Trip;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "trips")
public interface TripsProxy {

    @PostMapping("/trips")
    ResponseEntity<Trip> createOne(@RequestBody NewTrip newTrip);

    @GetMapping("/trips")
    ResponseEntity<Iterable<Trip>> readAll();

    @GetMapping("/trips/{id}")
    ResponseEntity<Trip> readOne(@PathVariable int id);

    @DeleteMapping("/trips/{id}")
    ResponseEntity<Trip> deleteOne(@PathVariable int id);

    @GetMapping("/trips/driver/{id}")
    Iterable<Trip> readOneByDriver(@PathVariable int id);

    @DeleteMapping("/trips/driver/{id}")
    ResponseEntity<Iterable<Trip>> deleteAllByDriver(@PathVariable int id);



}
