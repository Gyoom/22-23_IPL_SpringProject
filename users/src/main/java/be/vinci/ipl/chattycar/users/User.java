package be.vinci.ipl.chattycar.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    private String pseudo;
    private String firstname;
    private String lastname;
}
