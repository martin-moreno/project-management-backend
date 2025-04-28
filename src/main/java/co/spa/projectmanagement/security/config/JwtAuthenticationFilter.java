package co.spa.projectmanagement.security.config;

import co.spa.projectmanagement.security.service.CustomUserDetailsService;
import co.spa.projectmanagement.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        logger.info("Intercepting path: {}", path);

        // Skip the filter for login and register paths
        if (path.startsWith("/api/auth/login") || path.startsWith("/api/auth/register")) {
            logger.info("Skipping filter for path: {}", path);
            filterChain.doFilter(request, response);
            return;
        }

        // Get the Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Check if the Authorization header is missing or not properly formatted
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Missing or invalid Authorization header");
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT and username
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);
        logger.info("JWT extracted for username: {}", username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // Load user details
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // Validate the JWT token
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    logger.info("Token valid for user: {}", username);

                    // Create authentication token and set it in the SecurityContext
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.debug("User authenticated successfully, setting authentication in context.");
                } else {
                    logger.warn("Invalid token for user: {}", username);
                }
            } catch (Exception e) {
                logger.error("Error processing JWT authentication for user: {}", username, e);
            }
        } else {
            logger.warn("No valid authentication token found for username: {}", username);
        }

        filterChain.doFilter(request, response);
    }
}