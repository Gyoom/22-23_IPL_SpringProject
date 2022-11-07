package com.example.mock_trips;

import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockTripsController {

    @GetMapping("/trips/{id}")
    public ResponseEntity<Trip> createOne(@PathVariable("id") int id) {
        System.out.println(id);
        return new ResponseEntity<>(
            new Trip(id, new Position(3, 3), new Position(4, 4), LocalDate.now(), 3, 5),
            HttpStatus.ACCEPTED
        );
    }

}
