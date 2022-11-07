package be.vinci.ipl.chattycar.notifications;

import be.vinci.ipl.chattycar.notifications.data.NotificationsRepository;
import be.vinci.ipl.chattycar.notifications.models.NoIdNotification;
import be.vinci.ipl.chattycar.notifications.models.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationsServices {
  private final NotificationsRepository repository;

  public NotificationsServices(NotificationsRepository repository) {
    this.repository = repository;
  }

  public boolean createNotification(NoIdNotification noIdNotification) {
    if (noIdNotification.getDate() == null || noIdNotification.getNotificationText() == null
        || noIdNotification.getTripId() == 0 || noIdNotification.getUserId() == 0)
      return false;
    System.out.println(noIdNotification.toNotification());
    repository.save(noIdNotification.toNotification());
    return true;
  }

  public Iterable<Notification> getNotifications(int userId) {
    return repository.findAllByUserId(userId);
  }

  public void deleteNotification(int userId) {
    System.out.println("bug here");
    repository.deleteAllByUserId(userId);
  }

}
