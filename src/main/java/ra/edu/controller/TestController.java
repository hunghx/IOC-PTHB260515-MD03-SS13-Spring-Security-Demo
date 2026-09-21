package ra.edu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

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
}
