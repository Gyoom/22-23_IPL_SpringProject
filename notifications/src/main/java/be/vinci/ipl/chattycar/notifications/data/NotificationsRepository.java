package be.vinci.ipl.chattycar.notifications.data;

import be.vinci.ipl.chattycar.notifications.models.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepository extends CrudRepository<Notification, Long> {
}
