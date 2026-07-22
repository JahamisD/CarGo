package cit.edu.inosanto.backend.CarGo.features.users.service;

import cit.edu.inosanto.backend.CarGo.features.users.dto.UpdateProfileRequest;
import cit.edu.inosanto.backend.CarGo.features.users.entity.User;
import cit.edu.inosanto.backend.CarGo.features.users.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User updateProfile(Long id, UpdateProfileRequest request){

        User user = userRepository.findById(id).orElse(null);

        if(user == null){
            return null;
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }

}