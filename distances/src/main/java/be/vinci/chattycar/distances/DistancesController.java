package be.vinci.chattycar.distances;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@RestController
public class DistancesController {
    @GetMapping("/distances")
    public double distanceBetween(@QueryParam("lat1") double lat1, @QueryParam("lon1") double lon1, @QueryParam("lat2") double lat2, @QueryParam("lon2") double lon2) {
        //TODO(Eliott) est ce qu'il faut checker si les QueryParams sont là ou bien est ce qu'il le fait tout seul vu que j'ai mis dans le .yaml "required: true"
        return Math.sqrt(Math.pow(lat1-lat2, 2) + Math.pow(lon1-lon2, 2));
    }

}
