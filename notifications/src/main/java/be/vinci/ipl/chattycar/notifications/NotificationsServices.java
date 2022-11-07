package be.vinci.ipl.chattycar.notifications;

import be.vinci.ipl.chattycar.notifications.data.NotificationsRepository;
import be.vinci.ipl.chattycar.notifications.data.UsersProxy;
import org.springframework.stereotype.Service;

@Service
public class NotificationsServices {
  private final NotificationsRepository repository;
  private final UsersProxy usersProxy;

  public NotificationsServices(NotificationsRepository repository, UsersProxy usersProxy) {
    this.repository = repository;
    this.usersProxy = usersProxy;
  }

}
