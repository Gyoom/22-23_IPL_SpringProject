package be.vinci.ipl.chattycar.passengers.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class NoIdUser {
  private String email;
  private String firstname;
  private String lastname;
}
