package in.service.user.feignclient;

import in.service.user.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "hotel-service")
public interface HotelService {
    @GetMapping("/hotels/{hotelId}")
    Hotel getHotelDetail(@PathVariable String hotelId);

    //similarly we can create all the HTTP requests i.e
    //GET, POST, DELETE, PUT, PATCH
}
