package cit.edu.inosanto.backend.CarGo.features.users.controller;

import cit.edu.inosanto.backend.CarGo.features.users.dto.UpdateProfileRequest;
import cit.edu.inosanto.backend.CarGo.features.users.entity.User;
import cit.edu.inosanto.backend.CarGo.features.users.repository.UserRepository;
import cit.edu.inosanto.backend.CarGo.features.users.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // ==========================
    // AUTH
    // ==========================

    @PostMapping("/api/auth/register")
    public User register(@RequestBody User newUser) {

        newUser.setRole("CUSTOMER");

        return userRepository.save(newUser);
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {

        User user = userRepository.findByEmail(loginUser.getEmail());

        if (user == null) {
            return ResponseEntity
                    .status(401)
                    .body(Map.of("message", "Email not found"));
        }

        if (!user.getPassword().equals(loginUser.getPassword())) {
            return ResponseEntity
                    .status(401)
                    .body(Map.of("message", "Wrong password"));
        }

        // Don't return the password
        user.setPassword(null);

        return ResponseEntity.ok(
                Map.of("user", user));
    }

    // ==========================
    // ACCOUNT MANAGEMENT
    // ==========================

    @GetMapping("/api/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){

        User user = userService.getUserById(id);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        user.setPassword(null);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<?> updateProfile(
            @PathVariable Long id,
            @RequestBody UpdateProfileRequest request){

        User updatedUser = userService.updateProfile(id, request);

        if(updatedUser == null){
            return ResponseEntity.notFound().build();
        }

        updatedUser.setPassword(null);

        return ResponseEntity.ok(updatedUser);
    }

}