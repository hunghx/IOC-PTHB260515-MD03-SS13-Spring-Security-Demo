package ra.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ra.edu.config.jwt.JwtService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestController {
    private final JwtService jwtService;

    @GetMapping("/admin/test") // quyền ADMIN
    public String testAdmin() {
        return "Hello, Admin!";
    }

    @GetMapping("/user/test")  // quyền USER|ADMIN
    public String testUser() {
        return "Hello, User!";
    }

    @GetMapping("/public/test") // công khai
    public String testPublic() {
        return "Hello, Public!";
    }
    @GetMapping("/demo/test") // bất cứ quyền gi đều có thể truy cập
    public String testDemo() {
        return "Hello, Demo!";
    }

    @GetMapping("/jwt/create")
    public String createJwt(@RequestParam String username) {
        return jwtService.generateToken(username);
    }
    @GetMapping("/jwt/validate")
    public boolean validateToken(@RequestParam String token) {
        return jwtService.validateToken(token);
    }
     @GetMapping("/jwt/parser")
    public String parserToken(@RequestParam String token) {
        return jwtService.extractUsername(token);
    }


}
