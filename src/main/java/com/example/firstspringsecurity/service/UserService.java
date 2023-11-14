package com.example.firstspringsecurity.service;


import com.example.firstspringsecurity.dto.RegisterDto;
import com.example.firstspringsecurity.entity.Role;
import com.example.firstspringsecurity.entity.User;
import com.example.firstspringsecurity.enums.RoleName;
import com.example.firstspringsecurity.repository.IUserRepository;
import com.example.firstspringsecurity.security.JwtUtilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j // Logger message to the console
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtilities;

    // Register a new user and generate a JWT token.
    public ResponseEntity<String> registerUser(RegisterDto registerDto) {
        try {
            // Convert DTO to User entity
            User user = convertToUser(registerDto);

            // Encode password and save user
            encodeAndSaveUser(user);

            // Generate JWT token
            String token = jwtUtilities.generateToken(registerDto.getEmail(), Collections.singletonList(String.valueOf(RoleName.ROLE_USER)));

            // Return success response with JWT token
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User registered successfully. Token: " + token);
        } catch (DataIntegrityViolationException ex) {
            // Return error response if email is already in use
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email is already in use.");
        } catch (Exception ex) {
            // Return internal server error response for other exceptions
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occurred: " + ex.getMessage());
        }
    }

    // Encode password and save user.
    private void encodeAndSaveUser(User user) {
        // Encode user's password
        String hashedPassword = passwordEncoder.encode(user.getPassword());

        // Set the hashed password
        user.setPassword(hashedPassword);

        // Save user to the repository
        userRepository.save(user);

        // Log registration information
        log.info("User registered: {}", user);
    }

    // Convert registration DTO to User entity.
    private User convertToUser(RegisterDto registerDto) {
        // Create a new User instance
        User user = new User();

        // Set user details from the DTO
        user.setEmail(registerDto.getEmail());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPassword(registerDto.getPassword());

        // Set default role as USER
        Role role = new Role(RoleName.ROLE_USER);
        user.setRoles(Collections.singletonList(role));

        return user;
    }

    // Load user details for authentication.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername called: user={}", username);

        // Retrieve user details from the repository based on the email (username)
        Optional<User> userOptional = userRepository.findByEmail(username);

        if (userOptional.isPresent()) {
            // Map user roles to Spring Security authorities
            User user = userOptional.get();
            List<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                    .collect(Collectors.toList());

            // Return a UserDetails object with user information
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    authorities
            );
        } else {
            // Throw an exception if user details are not found
            throw new UsernameNotFoundException("User details not found for the user: " + username);
        }
    }

    // Retrieve all users.
    public List<User> findAll() {
        // Retrieve and return a list of all users from the repository
        return userRepository.findAll();
    }
}
