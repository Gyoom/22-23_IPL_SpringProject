package be.vinci.ipl.chattycar.gateway.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
