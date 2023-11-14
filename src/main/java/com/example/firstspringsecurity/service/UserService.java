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

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtilities;

    public ResponseEntity<String> registerUser(RegisterDto registerDto) {
        try {
            User user = convertToUser(registerDto);
            encodeAndSaveUser(user);

            String token = jwtUtilities.generateToken(registerDto.getEmail(), Collections.singletonList(String.valueOf(RoleName.ROLE_USER)));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Given user details are successfully registered. Token: " + token);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email is already in use.");
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occurred due to " + ex.getMessage());
        }
    }

    private void encodeAndSaveUser(User user) {
        // Encode password before saving
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        // Save the user along with the provided roles
        userRepository.save(user);
        log.info("User registered: {}", user);
    }

    private User convertToUser(RegisterDto registerDto) {
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPassword(registerDto.getPassword());
        // Set default role as USER
        Role role = new Role(RoleName.ROLE_USER);
        user.setRoles(Collections.singletonList(role));

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername called: user={}", username);

        Optional<User> userOptional = userRepository.findByEmail(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                    .collect(Collectors.toList());

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    authorities
            );
        } else {
            throw new UsernameNotFoundException("User details not found for the user: " + username);
        }
    }



    public List<User> findAll() {
        return userRepository.findAll();
    }


}
