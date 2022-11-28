package be.vinci.ipl.chattycar.notifications.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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
  @JsonProperty("user_id")
  private int userId;
  @JsonProperty("trip_id")
  private int tripId;
  private LocalDate date;
  @JsonProperty("notification_text")
  private String notificationText;

  public Notification toNotification() {
    return new Notification(userId, tripId, date, notificationText);
  }
}
