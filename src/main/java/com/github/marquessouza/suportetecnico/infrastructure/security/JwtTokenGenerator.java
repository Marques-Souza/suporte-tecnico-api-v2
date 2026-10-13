package com.github.marquessouza.suportetecnico.infrastructure.security;

import com.github.marquessouza.suportetecnico.application.port.TokenGenerator;
import com.github.marquessouza.suportetecnico.domain.model.User;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtTokenGenerator implements TokenGenerator {

    private final JwtEncoder jwtEncoder;
    private final JwtProperties properties;

    public JwtTokenGenerator(JwtEncoder jwtEncoder, JwtProperties properties) {
        this.jwtEncoder = jwtEncoder;
        this.properties = properties;
    }

    @Override
    public String generateToken(User user){
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plus(properties.expirationMinutes(), ChronoUnit.MINUTES))
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
