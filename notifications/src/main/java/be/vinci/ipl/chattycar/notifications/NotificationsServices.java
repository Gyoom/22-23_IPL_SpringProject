package be.vinci.ipl.chattycar.notifications;

import be.vinci.ipl.chattycar.notifications.data.NotificationsRepository;
import be.vinci.ipl.chattycar.notifications.models.NoIdNotification;
import be.vinci.ipl.chattycar.notifications.models.Notification;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NotificationsServices {
  private final NotificationsRepository repository;

  public NotificationsServices(NotificationsRepository repository) {
    this.repository = repository;
  }

  /**
   * Add new notification.
   *
   * @param noIdNotification The new notification
   * @return true if the notification has been created or false
   */
  public NoIdNotification createNotification(NoIdNotification noIdNotification) {
    return repository.save(noIdNotification.toNotification()).toNoIdNotification();
  }

  /**
   * Get user notifications.
   *
   * @param userId The id of a user
   * @return All user notification
   */
  public List<NoIdNotification> getNotifications(int userId) {
    return repository.findAllByUserId(userId).stream().map(Notification::toNoIdNotification).toList();
  }

  /**
   * Delete user notifications.
   *
   * @param userId The id of a user
   */
  public void deleteNotification(int userId) {
    repository.deleteAllByUserId(userId);
  }

}
