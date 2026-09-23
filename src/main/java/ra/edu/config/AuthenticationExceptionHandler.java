package ra.edu.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
// Java web : servlet , jsp
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 401 : ko được xác thực

        // HTTP 401: Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401

        response.setContentType("application/json");

        response.getWriter().write(
                """
                {
                    "status": 401,
                    "message": "Unauthorized - Please login"
                }
                """
        );
    }
}
