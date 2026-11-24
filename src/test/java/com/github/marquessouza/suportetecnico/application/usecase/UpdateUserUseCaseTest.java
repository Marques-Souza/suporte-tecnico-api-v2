package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.UpdateUserRequest;
import com.github.marquessouza.suportetecnico.application.dto.UserResponse;
import com.github.marquessouza.suportetecnico.domain.exception.UserAlreadyExistsException;
import com.github.marquessouza.suportetecnico.domain.exception.UserNotFoundException;
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
public class UpdateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    private final UUID id = UUID.randomUUID();

    private final User existingUser(){
        return new User(id, "Ana", "ana@email.com", "hash-original", Role.ADMIN);
    }

    @Test
    void shouldUpdateNameAndEmailKeepingPasswordHashAndRole() {
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser()));
        when(userRepository.findByEmail("ana.souza@email.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = updateUserUseCase.execute(
                id, new UpdateUserRequest("Ana Souza", "ana.souza@email.com"));

        ArgumentCaptor<User> savedUser = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(savedUser.capture());
        assertEquals("Ana Souza", response.name());
        assertEquals("ana.souza@email.com", response.email());
        assertEquals("hash-original", savedUser.getValue().getPasswordHash());
        assertEquals(Role.ADMIN, savedUser.getValue().getRole());
    }

    @Test
    void shouldAllowKeepingTheSameEmail() {
        User existing = existingUser();
        when(userRepository.findById(id)).thenReturn(Optional.of(existing));
        when(userRepository.findByEmail("ana@email.com")).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = updateUserUseCase.execute(
                id, new UpdateUserRequest("Ana Souza", "ana@email.com"));

        assertEquals("Ana Souza", response.name());
    }

    @Test
    void shouldThrowWhenEmailBelongsToAnotherUser() {
        User anotherUser = new User(UUID.randomUUID(), "Bia", "bia@teste.com", "hash", Role.ATTENDANT);
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser()));
        when(userRepository.findByEmail("bia@teste.com")).thenReturn(Optional.of(anotherUser));

        assertThrows(UserAlreadyExistsException.class, () ->
                updateUserUseCase.execute(id, new UpdateUserRequest("Ana Souza", "bia@teste.com")));

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenUserDoesNotExist() {
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                updateUserUseCase.execute(id, new UpdateUserRequest("Ana Souza", "ana@teste.com")));

        verify(userRepository, never()).save(any());
    }
}
