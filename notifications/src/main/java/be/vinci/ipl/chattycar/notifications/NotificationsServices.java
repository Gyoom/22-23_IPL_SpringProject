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

  /**
   * Add new notification.
   *
   * @param noIdNotification The new notification
   * @return true if the notification has been created or false
   */
  public boolean createNotification(NoIdNotification noIdNotification) {
    if (noIdNotification.getDate() == null || noIdNotification.getNotificationText() == null
        || noIdNotification.getTripId() == 0 || noIdNotification.getUserId() == 0)
      return false;

    repository.save(noIdNotification.toNotification());
    return true;
  }

  /**
   * Get user notifications.
   *
   * @param userId The id of a user
   * @return All user notification
   */
  public Iterable<Notification> getNotifications(int userId) {
    return repository.findAllByUserId(userId);
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
