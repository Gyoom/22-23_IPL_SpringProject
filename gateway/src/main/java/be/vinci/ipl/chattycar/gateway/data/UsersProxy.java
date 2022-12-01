package be.vinci.ipl.chattycar.gateway.data;

import be.vinci.ipl.chattycar.gateway.models.User;
import be.vinci.ipl.chattycar.gateway.models.UserWithId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "users")
public interface UsersProxy {

    @PostMapping("/users")
    UserWithId createUser(@RequestBody User user);

    @GetMapping("/users")
    UserWithId readUser(@RequestParam("email") String email);

    @GetMapping("/users/{id}")
    UserWithId getOne(@PathVariable int id);

    @PutMapping("/users/{id}")
    void updateUser(@PathVariable int id, @RequestBody UserWithId user);

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable int id);

}
