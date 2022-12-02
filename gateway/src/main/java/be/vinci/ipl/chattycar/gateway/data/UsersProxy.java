package be.vinci.ipl.chattycar.gateway.data;

import be.vinci.ipl.chattycar.gateway.models.UserWithoutId;
import be.vinci.ipl.chattycar.gateway.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "users")
public interface UsersProxy {

    @PostMapping("/users")
    User createUser(@RequestBody UserWithoutId userWithoutId);

    @GetMapping("/users")
    User readUser(@RequestParam("email") String email);

    @GetMapping("/users/{id}")
    User getOne(@PathVariable int id);

    @PutMapping("/users/{id}")
    void updateUser(@PathVariable int id, @RequestBody User user);

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable int id);

}
