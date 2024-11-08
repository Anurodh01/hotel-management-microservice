package in.service.user.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import in.service.user.entities.User;
import in.service.user.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Value("${my-greeting}")
    private String greeting;
    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        System.out.println(greeting);
        return ResponseEntity.ok(userService.getAllUser());
    }
    int i= 0;
    @GetMapping("/{userId}")
    @CircuitBreaker(name = "rating-hotel-service-breaker", fallbackMethod = "ratingHotelServiceFallBackMethod")
    @Retry(name = "rating-hotel-service-retry", fallbackMethod = "ratingHotelServiceFallBackMethodForRetry")
    public ResponseEntity<User> getUserById(@PathVariable String userId){
        ++i;
        System.out.println("Attempt: "+i);
        log.info("Rating details for User: {}", userId);
        try {
            return ResponseEntity.ok(userService.getUserByUserId(userId));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<User> ratingHotelServiceFallBackMethod(String userId, Exception ex){
        User user = User.builder().userId("DummyID").email("dummyemail@gmail.com").phone("9999999999").address("dummyAddress").name("dummyUser").build();
        System.out.println(ex.getMessage());
        System.out.println(ex.getStackTrace());
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<User> ratingHotelServiceFallBackMethodForRetry(String userId, Exception ex){
        User user = User.builder().userId("DummyID").email("dummyemail@gmail.com").phone("9999999999").address("dummyAddress").name("dummyUser").build();
        System.out.println("All Attempts Exhausted!!");
        return ResponseEntity.ok(user);
    }
}
