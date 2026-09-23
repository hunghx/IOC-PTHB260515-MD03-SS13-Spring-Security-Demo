package ra.edu.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ra.edu.config.UserDetailsServiceCustom;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsServiceCustom userDetailsService;
    // lọc lấy jwt token từ header của request ,
    // validatetoken, giải mã token
    // Lưu đối tượng xác thực vào SecurityContext


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Can thiệp vào request trước khi đi vào các tầng tiếp theo
        String token = getTokenFromRequest(request);
        // Validate
        if (token!=null && jwtService.validateToken(token)){
            // giải mã
            String username = jwtService.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // UserDetail có ? thông tin :principle, credential, authorities
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            // Lưu vào security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // muốn cho đi tiếp thi phải
        filterChain.doFilter(request,response);
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");

        // cắt chuỗi Bearer
        if (authorization !=null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }
}
