package be.vinci.ipl.catflix.gateway.data;

import be.vinci.ipl.catflix.gateway.models.Video;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "videos")
public interface VideosProxy {

    @GetMapping("/videos")
    Iterable<Video> readVideos();


    @PostMapping("/videos/{hash}")
    void createVideo(@PathVariable String hash, @RequestBody Video video);

    @GetMapping("/videos/{hash}")
    Video readVideo(@PathVariable String hash);

    @PutMapping("/videos/{hash}")
    void updateVideo(@PathVariable String hash, @RequestBody Video video);

    @DeleteMapping("/videos/{hash}")
    void deleteVideo(@PathVariable String hash);


    @GetMapping("/videos/author/{pseudo}")
    Iterable<Video> readVideosFromAuthor(@PathVariable String pseudo);

    @DeleteMapping("/videos/author/{pseudo}")
    void deleteVideosFromAuthor(@PathVariable String pseudo);

}
