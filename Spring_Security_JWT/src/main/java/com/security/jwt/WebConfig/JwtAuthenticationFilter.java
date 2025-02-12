package com.security.jwt.WebConfig;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.jwt.JwtUtil.JwtUtils;
import com.security.jwt.Service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal( @NonNull HttpServletRequest request,@NonNull  HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Extract the Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Check if the header is missing or doesn't start with "Bearer"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);  // Proceed with the next filter
            return;
        }

        // Extract the JWT from the Authorization header
        jwt = authHeader.substring(7);  // Remove "Bearer " prefix
        userEmail = jwtUtils.extractUserName(jwt);  // Extract userEmail from JWT

        // If the userEmail is not empty and the user is not already authenticated
        if (userEmail != null && !userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load the user details from the user service
            UserDetails userDetails = userService.loadUserByUsername(userEmail);

            // If the token is valid
            if (jwtUtils.isTokenValid(jwt, userDetails)) {

                // Create a new authentication token for the user
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Set additional details (such as request details)
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the security context
                context.setAuthentication(token);
                SecurityContextHolder.setContext(context);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
