package be.vinci.ipl.chattycar.users;

import be.vinci.ipl.chattycar.users.models.NewUser;
import be.vinci.ipl.chattycar.users.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UsersController {

    private final UsersService service;

    public UsersController(UsersService service) {
        this.service = service;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody NewUser newUser) {
        if (newUser.getEmail() == null || newUser.getFirstname() == null ||
                newUser.getLastname() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User createdUser = service.createOne(newUser);
        if (createdUser == null) throw new ResponseStatusException(HttpStatus.CONFLICT);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public User readUser(@RequestParam(value = "email") String email) {
        User user = service.findByEmail(email);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return user;
    }

    @GetMapping("/users/{id}")
    public User getOne(@PathVariable int id) {
        User user = service.getOne(id);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return user;
    }

    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User user) {
        if (id != user.getId() || user.getEmail() == null ||
                user.getLastname() == null || user.getFirstname() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        boolean found = service.updateOne(user);
        if (!found) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/users/{id}")
    public void deleteOne(@PathVariable int id) {
        boolean found = service.deleteOne(id);
        if (!found) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
