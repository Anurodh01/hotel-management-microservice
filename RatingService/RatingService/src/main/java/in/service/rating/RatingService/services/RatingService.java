package in.service.rating.RatingService.services;

import in.service.rating.RatingService.entities.Rating;

import java.util.List;

public interface RatingService {
    Rating createRating(Rating rating);

    List<Rating> getListOfRatingByUserId(String userId);

    List<Rating> getListOfRatingByHotelId(String hotelId);

    List<Rating> getAllRating();

    Rating getRatingById(String ratingId);
}
