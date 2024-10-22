package com.example.mobi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mobi.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    Optional<User> findByUsername(String username);
}
