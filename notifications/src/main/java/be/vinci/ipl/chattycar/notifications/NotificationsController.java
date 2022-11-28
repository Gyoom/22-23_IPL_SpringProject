package be.vinci.ipl.chattycar.notifications;

import be.vinci.ipl.chattycar.notifications.models.NoIdNotification;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class NotificationsController {
  private final NotificationsServices service;

  public NotificationsController(NotificationsServices service) {
    this.service = service;
  }

  /**
   * Add a notification.
   *
   * @param noIdNotification The new notification
   * @return 200 status code with the created notification if the notification has been created or 400
   */
  @PostMapping("/notifications")
  public ResponseEntity<NoIdNotification> createNotification(@RequestBody NoIdNotification noIdNotification) {
    if (noIdNotification.getNotificationText() == null
        || noIdNotification.getTripId() == 0
        || noIdNotification.getUserId() == 0
        || noIdNotification.getDate() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    NoIdNotification newNoIdNotification = service.createNotification(noIdNotification);
    if (newNoIdNotification == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(newNoIdNotification, HttpStatus.CREATED);
  }

  /**
   * Get user notifications.
   *
   * @param userId The id of a user
   * @return 200 status code with all user notifications
   */
  @GetMapping("/notifications/{user_id}")
  public ResponseEntity<List<NoIdNotification>> getNotifications(@PathVariable("user_id") int userId) {
    return new ResponseEntity<>(service.getNotifications(userId), HttpStatus.OK);
  }

  /**
   * Delete all user notifications.
   *
   * @param userId The id of a user
   */
  @DeleteMapping("/notifications/{user_id}")
  public void deleteNotification(@PathVariable("user_id") int userId) {
    service.deleteNotification(userId);
  }
}
