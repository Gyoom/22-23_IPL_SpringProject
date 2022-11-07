package be.vinci.ipl.chattycar.notifications.models;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "notifications")
public class Notification {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int notificationId;
  @Column(name = "user_id")
  private int userId;
  @Column(name = "trip_id")
  private int tripId;
  @Column(name = "date")
  private LocalDate date;
  @Column(name = "notification_text")
  private String notificationText;

  public Notification(int userId, int tripId, LocalDate date, String notificationText) {
    this.userId = userId;
    this.tripId = tripId;
    this.date = date;
    this.notificationText = notificationText;
  }
}
