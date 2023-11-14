package com.example.firstspringsecurity.repository;

import com.example.firstspringsecurity.entity.Role;
import com.example.firstspringsecurity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role,Integer> {
    Role findByRoleName(RoleName roleName);
}