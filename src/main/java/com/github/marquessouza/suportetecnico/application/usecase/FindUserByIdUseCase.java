package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.UserResponse;
import com.github.marquessouza.suportetecnico.domain.exception.UserNotFoundException;
import com.github.marquessouza.suportetecnico.domain.model.User;
import com.github.marquessouza.suportetecnico.domain.repository.UserRepository;

import java.util.UUID;

public class FindUserByIdUseCase {

    private final UserRepository userRepository;

    public FindUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse execute(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        return UserResponse.from(user);
    }
}
