package be.vinci.ipl.chattycar.notifications.models;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoIdNotification {
  private int userId;
  private int tripId;
  private LocalDate date;
  private String notificationText;

  public Notification toNotification() {
    return new Notification(userId, tripId, date, notificationText);
  }
}
