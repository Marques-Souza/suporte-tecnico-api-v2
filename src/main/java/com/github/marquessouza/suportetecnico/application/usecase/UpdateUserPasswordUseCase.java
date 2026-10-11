package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.UpdateUserPasswordRequest;
import com.github.marquessouza.suportetecnico.application.port.PasswordHash;
import com.github.marquessouza.suportetecnico.domain.exception.UserNotFoundException;
import com.github.marquessouza.suportetecnico.domain.model.User;
import com.github.marquessouza.suportetecnico.domain.repository.UserRepository;

import java.util.UUID;

public class UpdateUserPasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordHash passwordHash;

    public UpdateUserPasswordUseCase(UserRepository userRepository, PasswordHash passwordHash) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
    }

    public void execute(UUID id, UpdateUserPasswordRequest request){
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        User updatedUser = new User(
                user.getId(),
                user.getName(),
                user.getEmail(),
                passwordHash.hash(request.newPassword()),
                user.getRole()
        );
        userRepository.save(updatedUser);
    }
}
