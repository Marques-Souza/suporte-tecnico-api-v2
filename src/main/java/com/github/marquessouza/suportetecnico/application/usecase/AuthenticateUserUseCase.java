package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.LoginRequest;
import com.github.marquessouza.suportetecnico.application.dto.LoginResponse;
import com.github.marquessouza.suportetecnico.application.port.PasswordHash;
import com.github.marquessouza.suportetecnico.application.port.TokenGenerator;
import com.github.marquessouza.suportetecnico.domain.exception.InvalidCredentialsException;
import com.github.marquessouza.suportetecnico.domain.model.User;
import com.github.marquessouza.suportetecnico.domain.repository.UserRepository;

public class AuthenticateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordHash passwordHash;
    private final TokenGenerator tokenGenerator;

    public AuthenticateUserUseCase(UserRepository userRepository, PasswordHash passwordHash, TokenGenerator tokenGenerator) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
        this.tokenGenerator = tokenGenerator;
    }

    public LoginResponse execute(LoginRequest request){
        User user = userRepository.findByEmail(request.email())
                .filter(found -> passwordHash.matches(request.password(), found.getPasswordHash()))
                .orElseThrow(InvalidCredentialsException::new);
        return new LoginResponse(tokenGenerator.generateToken(user));
    }
}
