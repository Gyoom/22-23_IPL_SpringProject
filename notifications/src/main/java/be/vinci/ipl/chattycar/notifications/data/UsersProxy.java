package be.vinci.ipl.chattycar.notifications.data;

import be.vinci.ipl.chattycar.notifications.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(name = "users")
public interface UsersProxy {
  @GetMapping("/users/{user_id}")
  User readUser(@PathVariable("user_id") int id);
}
