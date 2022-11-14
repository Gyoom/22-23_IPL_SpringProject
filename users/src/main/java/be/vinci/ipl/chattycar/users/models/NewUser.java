package be.vinci.ipl.chattycar.users.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NewUser {
    private String email;
    private String firstname;
    private String lastname;
}
