package com.example.mobi.security;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncoder {

    public static String hashPassword(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

    public static String generateSalt() {
        return BCrypt.gensalt();
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
