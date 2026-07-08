package cit.edu.inosanto.backend.CarGo.features.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public User register(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @PostMapping("/login")
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

        return ResponseEntity.ok(
                Map.of("user", user)
        );
    }
}