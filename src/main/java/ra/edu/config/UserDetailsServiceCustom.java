package ra.edu.dto.request;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.edu.entity.User;
import ra.edu.repository.IUserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceCustom implements UserDetailsService {
    private final IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String keyword) throws UsernameNotFoundException {
        // ghi đè lại logic mặc định
        User user = userRepository.findByUsernameOrEmail(keyword,keyword).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + keyword)
        );
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
