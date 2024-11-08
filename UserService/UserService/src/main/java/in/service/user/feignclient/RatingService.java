package in.service.user.feignclient;

import in.service.user.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "rating-service")
public interface RatingService {
    @GetMapping("ratings/user/{userId}")
    List<Rating> getRatingByUserId(@PathVariable String userId);
}
