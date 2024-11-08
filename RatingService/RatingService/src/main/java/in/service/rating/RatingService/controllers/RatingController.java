package in.service.rating.RatingService.controllers;

import in.service.rating.RatingService.entities.Rating;
import in.service.rating.RatingService.services.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
@Slf4j
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating){
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.createRating(rating));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingForHotel(@PathVariable String hotelId){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getListOfRatingByHotelId(hotelId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rating>> getRatingByASpecificUser(@PathVariable String userId){
        log.info("Rating for user: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getListOfRatingByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRating(){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAllRating());
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<Rating> getRatingById(@PathVariable String ratingId){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getRatingById(ratingId));
    }
}
