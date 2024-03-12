package com.JPA.SignIn.SignUp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.JPA.SignIn.SignUp.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
        Optional<Role> findByName(String name);

    }

