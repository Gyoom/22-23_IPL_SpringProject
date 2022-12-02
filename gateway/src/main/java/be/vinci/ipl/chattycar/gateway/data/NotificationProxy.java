package be.vinci.ipl.chattycar.gateway.data;

import be.vinci.ipl.chattycar.gateway.models.Notification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Repository
@FeignClient(name = "notifications")
public interface NotificationProxy {

    @PostMapping("/notifications")
    Notification createNotification(@RequestBody Notification notification);

    @GetMapping("/notifications/{user_id}")
    List<Notification> getNotifications(@PathVariable("user_id") int userId);

    @DeleteMapping("/notifications/{user_id}")
    void deleteNotification(@PathVariable("user_id") int userId);

}
