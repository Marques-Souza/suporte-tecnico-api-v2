package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.CreateUserRequest;
import com.github.marquessouza.suportetecnico.application.dto.UserResponse;
import com.github.marquessouza.suportetecnico.application.port.PasswordHash;
import com.github.marquessouza.suportetecnico.domain.exception.UserAlreadyExistsException;
import com.github.marquessouza.suportetecnico.domain.model.User;
import com.github.marquessouza.suportetecnico.domain.repository.UserRepository;

import java.util.UUID;

public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordHash passwordHash;

    public CreateUserUseCase(UserRepository userRepository, PasswordHash passwordHash) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
    }

    public UserResponse execute(CreateUserRequest request){
        ensureEmailIsNotRegistered(request.email());

        User user = new User(
                UUID.randomUUID(),
                request.name(),
                request.email(),
                passwordHash.hash(request.password()),
                request.role()
        );

        return UserResponse.from(userRepository.save(user));
    }

    private void ensureEmailIsNotRegistered(String email){
        if (userRepository.findByEmail(email).isPresent()){
            throw new UserAlreadyExistsException();
        }
    }

}
