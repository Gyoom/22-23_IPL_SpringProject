package be.vinci.ipl.chattycar.gateway.data;

import be.vinci.ipl.chattycar.gateway.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "users")
public interface UsersProxy {

    @PostMapping("/users/{pseudo}")
    void createUser(@PathVariable String pseudo, @RequestBody User user);

    @GetMapping("/users/{pseudo}")
    User readUser(@PathVariable String pseudo);

    @PutMapping("/users/{pseudo}")
    void updateUser(@PathVariable String pseudo, @RequestBody User user);

    @DeleteMapping("/users/{pseudo}")
    void deleteUser(@PathVariable String pseudo);

}
