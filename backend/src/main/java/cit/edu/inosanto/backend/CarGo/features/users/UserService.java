package cit.edu.inosanto.backend.CarGo.features.users;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


}
