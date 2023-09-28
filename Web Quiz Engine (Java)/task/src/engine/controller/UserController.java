package engine.controller;

import engine.DTO.RegisterRequest;
import engine.model.User;
import engine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<String> registerUser(@Validated @RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent())
            return ResponseEntity.badRequest().build();

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), encodedPassword);
        user = userRepository.save(user);

        return ResponseEntity.ok("User " + request.getEmail() + " successfully added");
    }
}
