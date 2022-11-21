package be.vinci.ipl.chattycar.notifications.data;

import be.vinci.ipl.chattycar.notifications.models.Notification;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NotificationsRepository extends CrudRepository<Notification, Long> {
  List<Notification> findAllByUserId(int userId);
  @Transactional
  void deleteAllByUserId(int userId);
}
