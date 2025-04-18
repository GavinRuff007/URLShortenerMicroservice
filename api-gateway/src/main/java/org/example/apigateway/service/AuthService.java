package org.example.apigateway.service;

import lombok.RequiredArgsConstructor;
import org.example.apigateway.dto.JwtResponse;
import org.example.apigateway.dto.LoginRequest;
import org.example.apigateway.exception.InvalidCredentialsException;
import org.example.apigateway.repository.UserRepository;
import org.example.apigateway.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    public Mono<JwtResponse> login(LoginRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .flatMap(user -> {
                    String storedPassword = user.getPassword();
                    if (passwordEncoder.matches(request.getPassword(), storedPassword)) {

                        String token = jwtTokenProvider.createToken(request.getUsername());
                        return Mono.just(new JwtResponse(token));
                    } else {
                        throw new InvalidCredentialsException("Invalid credentials, you username or password not correct");
                    }
                });
    }
}
