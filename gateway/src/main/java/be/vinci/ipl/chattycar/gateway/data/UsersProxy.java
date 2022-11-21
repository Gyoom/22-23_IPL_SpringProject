package be.vinci.ipl.chattycar.gateway.data;

import be.vinci.ipl.chattycar.gateway.models.User;
import be.vinci.ipl.chattycar.gateway.models.UserWithId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "users")
public interface UsersProxy {

    @PostMapping("/users/{email}")
    void createUser(@PathVariable String email, @RequestBody User user);

    @GetMapping("/users/{pseudo}")
    User readUser(@PathVariable String pseudo);

    @PutMapping("/users/{id}")
    void updateUser(@PathVariable int id, @RequestBody UserWithId user);

    @DeleteMapping("/users/{email}")
    void deleteUser(@PathVariable String email);

}
