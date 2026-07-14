package cit.edu.inosanto.backend.CarGo.features.users.service;


import cit.edu.inosanto.backend.CarGo.features.users.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


}
