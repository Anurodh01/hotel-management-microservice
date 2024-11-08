package in.service.rating.RatingService.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.service.rating.RatingService.entities.Rating;
import in.service.rating.RatingService.exceptions.ResourceNotFoundException;
import in.service.rating.RatingService.repository.RatingRepository;
import in.service.rating.RatingService.services.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Override
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getListOfRatingByUserId(String userId) {
        List<Rating> ratings = ratingRepository.getByUserId(userId);
        try {
            log.info("Ratings Related to user: {}, Ratings: {}", userId , new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(ratings));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ratings;
    }

    @Override
    public List<Rating> getListOfRatingByHotelId(String hotelId) {
        return ratingRepository.getByHotelId(hotelId);
    }

    @Override
    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating getRatingById(String ratingId) {
        return ratingRepository.findById(ratingId).orElseThrow(()-> new ResourceNotFoundException("Rating Not found for Id: "+ratingId));
    }
}
