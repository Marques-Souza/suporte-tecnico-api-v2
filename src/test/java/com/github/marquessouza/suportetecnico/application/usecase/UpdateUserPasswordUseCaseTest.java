package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.UpdateUserPasswordRequest;
import com.github.marquessouza.suportetecnico.application.port.PasswordHash;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateUserPasswordUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHash passwordHash;

    @InjectMocks
    private UpdateUserPasswordUseCase updateUserPasswordUseCase;

    private final UUID id = UUID.randomUUID();

    @Test
    void shouldReplacePasswordHashKeepingOtherData() {
        User existing = new User(id, "Ana Admin", "ana@teste.com", "hash-antigo", Role.ADMIN);
        when(userRepository.findById(id)).thenReturn(Optional.of(existing));
        when(passwordHash.hash("novasenha123")).thenReturn("hash-novo");

        updateUserPasswordUseCase.execute(id, new UpdateUserPasswordRequest("novasenha123"));

        ArgumentCaptor<User> savedUser = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(savedUser.capture());
        assertEquals("hash-novo", savedUser.getValue().getPasswordHash());
        assertEquals("ana@teste.com", savedUser.getValue().getEmail());
        assertEquals(Role.ADMIN, savedUser.getValue().getRole());
    }

    @Test
    void shouldThrowWhenUserDoesNotExist() {
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                updateUserPasswordUseCase.execute(id, new UpdateUserPasswordRequest("novasenha123")));

        verify(userRepository, never()).save(any());
    }
}
