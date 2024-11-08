package in.service.user.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import in.service.user.entities.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    List<User> getAllUser();

    User getUserByUserId(String userId) throws JsonProcessingException;

}
