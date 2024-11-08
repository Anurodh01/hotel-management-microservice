package in.service.rating.RatingService.repository;

import in.service.rating.RatingService.entities.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Rating,String> {
    List<Rating> getByUserId(String userId);

    List<Rating> getByHotelId(String hotelId);
}
