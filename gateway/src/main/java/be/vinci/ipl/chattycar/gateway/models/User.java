package be.vinci.ipl.chattycar.gateway.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String email;
    private String firstname;
    private String lastname;
}
