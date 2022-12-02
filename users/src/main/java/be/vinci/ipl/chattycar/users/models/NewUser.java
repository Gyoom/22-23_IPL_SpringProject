package be.vinci.ipl.chattycar.users.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewUser {
    private String email;
    private String firstname;
    private String lastname;
}
