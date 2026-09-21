package ra.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.edu.dto.request.FormLogin;
import ra.edu.dto.request.FormRegister;
import ra.edu.entity.User;
import ra.edu.repository.IUserRepository;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // login
    @PostMapping("/login")
    public String login(@RequestBody FormLogin request){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            String uuid = UUID.randomUUID().toString();
            return "Login successful: " + uuid;
        }catch (Exception e){
            return "Login failed: " + e.getMessage();
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody FormRegister request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        userRepository.save(user);
        return "Register successful: " + user.getUsername();
    }
}
