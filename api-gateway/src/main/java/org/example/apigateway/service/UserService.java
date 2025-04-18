package org.example.apigateway.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void encodePassword(String password) {
        String encodedPassword = passwordEncoder.encode(password);
        System.out.println(encodedPassword);
    }
}
