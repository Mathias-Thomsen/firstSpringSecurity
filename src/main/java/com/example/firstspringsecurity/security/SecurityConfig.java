package com.example.firstspringsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    // Injecting JwtAuthenticationFilter and CustomerUserDetailsService
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomerUserDetailsService customerUserDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomerUserDetailsService customerUserDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customerUserDetailsService = customerUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CSRF protection for simplicity
        http.csrf(AbstractHttpConfigurer::disable)
                // Configuring authorization for different endpoints
                .authorizeHttpRequests(authorizeRequests -> {
                    // Restrict access based on roles
                    authorizeRequests.requestMatchers("/user").hasAnyRole("ADMIN", "USER")
                            .requestMatchers("/admin").hasRole("ADMIN")
                            .requestMatchers("/allAccess", "/register").permitAll();
                })
                // Configuring form-based login (can be customized further)
                .formLogin(Customizer.withDefaults())
                // Configuring HTTP Basic authentication (can be customized further)
                .httpBasic(Customizer.withDefaults())
                // Adding JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Using BCryptPasswordEncoder with a strength of 12
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // Creating a DaoAuthenticationProvider for custom user details and password encoding
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customerUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}