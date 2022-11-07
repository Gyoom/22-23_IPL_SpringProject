package be.vinci.ipl.chattycar.passengers.data;

import be.vinci.ipl.chattycar.passengers.models.Trip;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "users")
public interface UsersProxy {
  @GetMapping("/users/{id}")
  Trip readUser(@PathVariable int id);
}
