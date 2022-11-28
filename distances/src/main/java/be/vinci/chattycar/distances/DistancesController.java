package be.vinci.chattycar.distances;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.QueryParam;

@RestController
public class DistancesController {
    @GetMapping("/distances")
    public double distanceBetween(@QueryParam("lat1") Double lat1, @QueryParam("lon1") Double lon1, @QueryParam("lat2") Double lat2, @QueryParam("lon2") Double lon2) {
        if (lat1 == null || lat2 == null || lon1 == null || lon2 == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return Math.sqrt(Math.pow(lat1-lat2, 2) + Math.pow(lon1-lon2, 2));
    }

}
