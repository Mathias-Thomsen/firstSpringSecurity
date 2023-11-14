package com.example.firstspringsecurity.security;

import com.example.firstspringsecurity.entity.User;
import com.example.firstspringsecurity.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final IUserRepository iUserRepository ;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return iUserRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found !"));
    }


}