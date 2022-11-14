package be.vinci.ipl.chattycar.notifications;

import be.vinci.ipl.chattycar.notifications.models.NoIdNotification;
import be.vinci.ipl.chattycar.notifications.models.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationsController {
  private final NotificationsServices service;

  public NotificationsController(NotificationsServices service) {
    this.service = service;
  }

  @PostMapping("/notifications")
  public ResponseEntity<String> createNotification(@RequestBody NoIdNotification noIdNotification) {
    if (service.createNotification(noIdNotification))
      return new ResponseEntity<>(HttpStatus.CREATED);
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping("/notifications/{user_id}")
  public ResponseEntity<Iterable<Notification>> getNotifications(@PathVariable("user_id") int userId) {
    return new ResponseEntity<>(service.getNotifications(userId), HttpStatus.OK);
  }

  @DeleteMapping("/notifications/{user_id}")
  public ResponseEntity<String> deleteNotification(@PathVariable("user_id") int userId) {
    service.deleteNotification(userId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
