package ra.edu;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ra.edu.entity.User;
import ra.edu.repository.IUserRepository;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(PasswordEncoder passwordEncoder, IUserRepository userRepository) {
        return args -> {
//            System.out.println("Encoded password for '123456':); " + passwordEncoder.encode("123456"));
//            System.out.println("Encoded password for '123456$':); " + passwordEncoder.encode("123456$"));
//            User admin = new User();
//            admin.setUsername("admin123");
//            admin.setPassword(passwordEncoder.encode("admin123"));
//            admin.setRole("ADMIN");
//
//            userRepository.save(admin);
//            User user = new User();
//            user.setUsername("hunghx");
//            user.setPassword(passwordEncoder.encode("123456"));
//            user.setRole("USER");
//            userRepository.save(user);

        };
    }
}
