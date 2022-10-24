package be.vinci.ipl.chattycar.authentication.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsecureCredentials {
    private String pseudo;
    private String password;

    public Credentials toCredentials(String hashedPassword) {
        return new Credentials(pseudo, hashedPassword);
    }
}
