package be.vinci.ipl.chattycar.gateway.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserWithCredentials {
    private String email;
    private String firstname;
    private String lastname;
    private String password;

    public UserWithoutId toUser() {
        return new UserWithoutId(email, firstname, lastname);
    }
    public Credentials toCredentials() {
        return new Credentials(email, password);
    }
}
