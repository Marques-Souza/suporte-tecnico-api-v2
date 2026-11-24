package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.UpdateUserRequest;
import com.github.marquessouza.suportetecnico.application.dto.UserResponse;
import com.github.marquessouza.suportetecnico.domain.exception.UserAlreadyExistsException;
import com.github.marquessouza.suportetecnico.domain.exception.UserNotFoundException;
import com.github.marquessouza.suportetecnico.domain.model.User;
import com.github.marquessouza.suportetecnico.domain.repository.UserRepository;

import java.util.UUID;

public class UpdateUserUseCase {

    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse execute(UUID id, UpdateUserRequest request){
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        ensureEmailIsNotUserByAnotherUser(request.email(), id);

        User updatedUser = new User(
                user.getId(),
                request.name(),
                request.email(),
                user.getPasswordHash(),
                user.getRole()

        );

        return UserResponse.from(userRepository.save(updatedUser));
    }

    private void ensureEmailIsNotUserByAnotherUser(String email, UUID userId){
        boolean userByAnotherUser = userRepository.findByEmail(email)
                .filter(other -> !other.getId().equals(userId))
                .isPresent();

        if (userByAnotherUser){
            throw new UserAlreadyExistsException();
        }
    }
}
