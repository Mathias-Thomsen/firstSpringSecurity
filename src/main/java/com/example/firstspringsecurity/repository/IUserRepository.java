package com.example.firstspringsecurity.repository;

import com.example.firstspringsecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}



