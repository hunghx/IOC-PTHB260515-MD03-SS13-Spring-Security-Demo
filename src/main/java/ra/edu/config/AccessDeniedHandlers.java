package ra.edu.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class AccessDeniedHandlers implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // trả về mã 403 : ko có quyền truy cập
        // HTTP 401: Unauthorized
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403

        response.setContentType("application/json");

        response.getWriter().write(
                """
                {
                    "status": 403,
                    "message": "Access Denied!"
                }
                """
        );
    }
}
