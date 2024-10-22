package com.example.mobi.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.mobi.repository.UserRepository;
import com.example.mobi.security.PasswordEncoder;
import com.example.mobi.user.User;

@RestController
public class UserController {

    @Autowired
    UserRepository repo;


    
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @GetMapping("/users/{username}")
    public User getUserByUsername(@PathVariable String username) {
        Optional<User> user = repo.findByUsername(username);
        return user.orElse(null);
    }
    
    

    @PostMapping("/user/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createUser(@RequestBody User user) {
        String salt = PasswordEncoder.generateSalt();
        String hashedPassword = PasswordEncoder.hashPassword(user.getHashedPassword(), salt);
        user.setSalt(salt);
        user.setHashedPassword(hashedPassword);
        repo.save(user);
    }

    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.OK)
    public User login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOpt = repo.findByUsername(loginRequest.getUsername());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (PasswordEncoder.checkPassword(loginRequest.getPassword(), user.getHashedPassword())) {
                return user;
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

    static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
