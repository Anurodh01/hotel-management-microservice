package in.service.user.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import in.service.user.entities.Hotel;
import in.service.user.entities.Rating;
import in.service.user.entities.User;
import in.service.user.exception.ResourceNotFoundException;
import in.service.user.feignclient.HotelService;
import in.service.user.feignclient.RatingService;
import in.service.user.repositories.UserRepository;
import in.service.user.services.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    @SneakyThrows
    public User getUserByUserId(String userId) {
        User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with Id: "+userId));
        log.info("User Found: {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(user));
        //fetch the ratings provided by user for the hotels
//        Rating[] ratingsList = restTemplate.getForObject("http://rating-service/ratings/user/" + userId, Rating[].class);
        //now get the hotel for which this rating is given
//        List<Rating> ratings = Arrays.stream(ratingsList).map(rating -> {
//            ResponseEntity<Hotel> hotel = restTemplate.getForEntity("http://hotel-service/hotels/" + rating.getHotelId(), Hotel.class);
//            rating.setHotel(hotel.getBody());
//            return rating;
//        }).collect(Collectors.toList());
//        user.setRatings(ratings);

        //using feignClient to fetch the rating and hotel data
        fetchDetailsUsingFeignClient(user);
        return user;
    }

    @Autowired
    private RatingService ratingService;

    @Autowired
    private HotelService hotelService;
    @SneakyThrows
    private void fetchDetailsUsingFeignClient(User user) {
        //ratingService
        List<Rating> ratingList = ratingService.getRatingByUserId(user.getUserId());
        log.info("Ratings Received: {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(ratingList));
            //fetch hotel service
        List<Rating> ratings = ratingList.stream().map(rating -> {
                Hotel hotel = hotelService.getHotelDetail(rating.getHotelId());
                rating.setHotel(hotel);
                return rating;
            }).collect(Collectors.toList());
        System.out.println(ratings);
        log.info("Final Ratings with hotels : {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(ratings));
        user.setRatings(ratings);
    }
}
