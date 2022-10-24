package be.vinci.ipl.chattycar.gateway.data;

import be.vinci.ipl.chattycar.gateway.models.Credentials;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "authentication")
public interface AuthenticationProxy {

    @PostMapping("/authentication/connect")
    String connect(@RequestBody Credentials credentials);

    @PostMapping("/authentication/verify")
    String verify(@RequestBody String token);

    @PostMapping("/authentication/{pseudo}")
    void createCredentials(@PathVariable String pseudo, @RequestBody Credentials credentials);

    @PutMapping("/authentication/{pseudo}")
    void updateCredentials(@PathVariable String pseudo, @RequestBody Credentials credentials);

    @DeleteMapping("/authentication/{pseudo}")
    void deleteCredentials(@PathVariable String pseudo);

}
