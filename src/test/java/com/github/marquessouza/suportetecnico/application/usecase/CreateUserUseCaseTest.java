package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.CreateUserRequest;
import com.github.marquessouza.suportetecnico.application.dto.UserResponse;
import com.github.marquessouza.suportetecnico.application.port.PasswordHash;
import com.github.marquessouza.suportetecnico.domain.exception.UserAlreadyExistsException;
import com.github.marquessouza.suportetecnico.domain.model.Role;
import com.github.marquessouza.suportetecnico.domain.model.User;
import com.github.marquessouza.suportetecnico.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHash passwordHash;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;


    private CreateUserRequest request(){
        return new CreateUserRequest("Ana Admin", "ana@email.com", "123456789", Role.ADMIN);
    }

    @Test
    void shouldSaveUserWithHashedPassword(){
        when(userRepository.findByEmail("ana@email.com")).thenReturn(Optional.empty());
        when(passwordHash.hash("123456789")).thenReturn("hash-da-senha");
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = createUserUseCase.execute(request());
        ArgumentCaptor<User> savedUser = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(savedUser.capture());
        assertEquals("hash-da-senha", savedUser.getValue().getPasswordHash());
        assertEquals(Role.ADMIN, response.role());
    }

    @Test
    void shouldThrowWhenEmailIsAlreadyRegistered(){
        User existing = new User(UUID.randomUUID(), "ana Admin", "ana@email.com", "hash", Role.ADMIN);
        when(userRepository.findByEmail("ana@email.com")).thenReturn(Optional.of(existing));

        assertThrows(UserAlreadyExistsException.class, () ->
                createUserUseCase.execute(request()));

        verify(userRepository, never()).save(any());
    }
}
