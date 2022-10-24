package be.vinci.ipl.catflix.gateway.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserWithCredentials {
    private String pseudo;
    private String firstname;
    private String lastname;
    private String password;

    public User toUser() {
        return new User(pseudo, firstname, lastname);
    }
    public Credentials toCredentials() {
        return new Credentials(pseudo, password);
    }
}
