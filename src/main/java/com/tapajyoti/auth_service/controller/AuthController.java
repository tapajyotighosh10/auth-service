package com.tapajyoti.auth_service.controller;

import com.tapajyoti.auth_service.dto.AuthRequest;
import com.tapajyoti.auth_service.util.Jwtutil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final Jwtutil jwtutil;

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        // Logic to generate token based on username
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            return jwtutil.genreateToken(authRequest.getUsername());
        } catch (Exception e) {
            throw e;
        }
    }
}
