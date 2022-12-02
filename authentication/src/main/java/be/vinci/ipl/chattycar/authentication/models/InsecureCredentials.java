package be.vinci.ipl.chattycar.authentication.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsecureCredentials {
    private String email;
    private String password;

    public Credentials toCredentials(String hashedPassword) {
        return new Credentials(email, hashedPassword);
    }
}
