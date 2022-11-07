package be.vinci.ipl.chattycar.notifications;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationsController {
  private final NotificationsServices service;

  public NotificationsController(NotificationsServices service) {
    this.service = service;
  }

  @PostMapping("/notifications")
  public ResponseEntity<String> getPassengerStatus() {
    return null;
  }
}
