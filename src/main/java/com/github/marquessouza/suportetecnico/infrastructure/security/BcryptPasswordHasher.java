package com.github.marquessouza.suportetecnico.infrastructure.security;

import com.github.marquessouza.suportetecnico.application.port.PasswordHash;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordHasher implements PasswordHash {
    private final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

    @Override
    public String hash(String rawPassword){
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String passwordHash){
        return passwordEncoder.matches(rawPassword, passwordHash);
    }
}
