package in.service.hotel.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.service.hotel.entities.Hotel;
import in.service.hotel.exception.ResourceNotFoundException;
import in.service.hotel.respository.HotelRepository;
import in.service.hotel.services.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository repository;
    @Override
    public Hotel createHotel(Hotel hotel) {
        hotel.setHotelId(UUID.randomUUID().toString());
        return repository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return repository.findAll();
    }

    @Override
    public Hotel getHotelById(String hotelId) {
        Hotel hotel = repository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("No Hotel data found with Hotel Id: " + hotelId));
        try {
            log.info("Hotel Info Found: {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(hotel));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return hotel;
    }
}
