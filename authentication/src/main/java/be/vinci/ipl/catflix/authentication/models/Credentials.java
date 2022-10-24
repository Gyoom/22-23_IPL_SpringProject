package be.vinci.ipl.catflix.authentication.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "credentials")
public class Credentials {
    @Id
    private String pseudo;
    @Column(name = "hashed_password")
    private String hashedPassword;
}
