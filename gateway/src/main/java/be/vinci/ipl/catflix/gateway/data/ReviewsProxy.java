package be.vinci.ipl.catflix.gateway.data;

import be.vinci.ipl.catflix.gateway.models.NoIdReview;
import be.vinci.ipl.catflix.gateway.models.Review;
import be.vinci.ipl.catflix.gateway.models.Video;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "reviews")
public interface ReviewsProxy {

    @PostMapping("/reviews")
    Review createReview(@RequestBody NoIdReview review);

    @GetMapping("/reviews/{id}")
    Review readReview(@PathVariable long id);

    @PutMapping("/reviews/{id}")
    void updateReview(@PathVariable long id, @RequestBody Review review);

    @DeleteMapping("/reviews/{id}")
    void deleteReview(@PathVariable long id);


    @GetMapping("/reviews/user/{pseudo}")
    Iterable<Review> readReviewsFromUser(@PathVariable String pseudo);

    @DeleteMapping("/reviews/user/{pseudo}")
    void deleteReviewsFromUser(@PathVariable String pseudo);


    @GetMapping("/reviews/video/{hash}")
    Iterable<Review> readReviewsOfVideo(@PathVariable String hash);

    @DeleteMapping("/reviews/video/{hash}")
    void deleteReviewsOfVideo(@PathVariable String hash);


    @GetMapping("/reviews/best")
    Iterable<Video> readBestVideos();

}
