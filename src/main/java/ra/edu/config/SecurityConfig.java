package ra.edu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {
    // Cơ chế mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // thuật toán mã hóa mật khẩu mặc định
    }
    // cấu hình lại thông tin đăng nhập của các tk người dùng
    @Bean
    public UserDetailsService userDetailsService(){
        // khai báo các tk mẫu
        UserDetails admin = User.withUsername("admin123")
                .password(passwordEncoder().encode("123456")) // mã hóa mk theo thuật toán
                .roles("ADMIN")
                .build();
        UserDetails user = User.withUsername("hunghx")
                .password(passwordEncoder().encode("123456"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

}
