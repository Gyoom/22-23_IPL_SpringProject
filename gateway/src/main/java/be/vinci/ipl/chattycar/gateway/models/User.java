package be.vinci.ipl.chattycar.gateway.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String pseudo;
    private String firstname;
    private String lastname;
}
