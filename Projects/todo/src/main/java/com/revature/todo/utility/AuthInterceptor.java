package com.revature.todo.utility;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Check for token in cookies
        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isEmpty()) {
                    try {
                        // Validate the JWT token using JWTUtility
                        JWTUtility.validateToken(cookie.getValue());
                        return true;
                    } catch (Exception e) {
                        // Token invalid or expired
                        response.sendRedirect("/index.html");
                        return false;
                    }
                }
            }
        }
        response.sendRedirect("/index.html"); // Redirect to login if not authenticated
        return false;
    }
}
