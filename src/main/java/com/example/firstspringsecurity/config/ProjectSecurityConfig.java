package com.example.firstspringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder; //Use is we don't want to encrypt the password
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> {
            requests.requestMatchers("/user").hasAnyRole("ADMIN", "USER")
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .requestMatchers("/allAccess", "/register").permitAll();
        });
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        return (SecurityFilterChain) http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //ingen kryptering return NoOpPasswordEncoder.getInstance();
        //med 20 tager det lang tid at kryptere return new BCryptPasswordEncoder(20);
        //Take strength of decrypt type as parametre 10 is default.
        return new BCryptPasswordEncoder(12);
    }


}
