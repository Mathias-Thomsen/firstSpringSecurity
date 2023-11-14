package com.example.firstspringsecurity.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Injecting JwtUtilities and CustomerUserDetailsService
    private final JwtUtilities jwtUtilities;
    private final CustomerUserDetailsService customerUserDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // Extracting JWT token from the request
        String token = jwtUtilities.getToken(request);

        // Validating the JWT token
        if (token != null && jwtUtilities.validateToken(token)) {
            // Extracting the username from the JWT token
            String email = jwtUtilities.extractUsername(token);

            // Loading user details from the user service
            UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);

            if (userDetails != null) {
                // Creating an authentication token and setting it in the SecurityContextHolder
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                log.info("authenticated user with email: {}", email);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continuing the filter chain
        filterChain.doFilter(request, response);
    }
}
