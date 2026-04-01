package com.revature.todo.utility;

import com.revature.todo.exception.AuthFail;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Only filter /todo endpoints
		String path = request.getRequestURI();
		if (path.startsWith("/todo")) {
			String token = null;
			if (request.getCookies() != null) {
				for (Cookie cookie : request.getCookies()) {
					if ("token".equals(cookie.getName())) {
						token = cookie.getValue();
						break;
					}
				}
			}
			try {
				if (token == null || token.isEmpty()) {
					throw new AuthFail("Missing auth token");
				}
				JWTUtility.validateToken(token);
				// Token valid, continue
				filterChain.doFilter(request, response);
			} catch (AuthFail e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Unauthorized: " + e.getMessage());
			}
		} else {
			// Not /todo, skip filtering
			filterChain.doFilter(request, response);
		}
	}
}
