package ra.edu.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // bật các cấu hình mặc định cho bảo mật web
@RequiredArgsConstructor
public class SecurityConfig {
    // Cơ chế mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // thuật toán mã hóa mật khẩu mặc định
    }
    // cấu hình lại thông tin đăng nhập của các tk người dùng
//    @Bean
//    public UserDetailsService userDetailsService(){
//        // khai báo các tk mẫu
//        UserDetails admin = User.withUsername("admin123")
//                .password(passwordEncoder().encode("123456")) // mã hóa mk theo thuật toán
//                .roles("ADMIN")
//                .build();
//        UserDetails user = User.withUsername("hunghx")
//                .password(passwordEncoder().encode("123456"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }
    private final UserDetailsServiceCustom userDetailsServiceCustom;

    // Phân quyền các nguoi dùng theo đường dẫn

    // Request -> Filter1 -> Sercurity ....-> filtern -> Controller
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // tắt xác thực csrf
        http.csrf(AbstractHttpConfigurer::disable)
                // phân quyền theo từng đường dẫn
                .authorizeHttpRequests(reqMatcher ->
                        reqMatcher.requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/api/public/**").permitAll() // công khai , không cần xác thực
                                .requestMatchers("/api/auth/**").permitAll() // công khai , không cần xác thực
                                .requestMatchers("/api/jwt/**").permitAll() // công khai , không cần xác thực
                                .anyRequest().authenticated() // cần phaải xác thực
                        )
                .httpBasic(Customizer.withDefaults()); // xác thực bằng http basic
        return http.build();


    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsServiceCustom);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
