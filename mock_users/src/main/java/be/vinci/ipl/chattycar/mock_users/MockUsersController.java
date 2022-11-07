package be.vinci.ipl.chattycar.mock_users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MockUsersController {

    @GetMapping("/users/{id}")
    public ResponseEntity<User> createOne(@PathVariable("id") int id) {
        System.out.println(id);
        return new ResponseEntity<>(
            new User(id, "alain.dupont@gmail.com", "Alain", "Dupont"),
            HttpStatus.ACCEPTED
        );
    }

}
