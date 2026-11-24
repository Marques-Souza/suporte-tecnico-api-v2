package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.ClientResponse;
import com.github.marquessouza.suportetecnico.application.dto.CreateClientRequest;
import com.github.marquessouza.suportetecnico.domain.exception.ClientAlreadyExistsException;
import com.github.marquessouza.suportetecnico.domain.model.Client;
import com.github.marquessouza.suportetecnico.domain.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateClientUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private CreateClientUseCase createClientUseCase;

    @Test
    void shouldCreateClientWhenCpfIsNotRegistered(){
        when(clientRepository.findByCpf("12345678909")).thenReturn(Optional.empty());
        when(clientRepository.save(any(Client.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ClientResponse response = createClientUseCase.execute(
                new CreateClientRequest("John Cleber", "12345678909", "61999999999"));

        assertNotNull(response.id());
        assertEquals("John Cleber", response.name());
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void shouldThrowWhenCpfIsAlreadyRegistered(){
        Client existing = new Client(UUID.randomUUID(), "John Cleber", "12345678909","61999999999");
        when(clientRepository.findByCpf("12345678909")).thenReturn(Optional.of(existing));

        assertThrows(ClientAlreadyExistsException.class, ()->
                createClientUseCase.execute(
                        new CreateClientRequest("John Cleber", "12345678909", "61999999999")));
        verify(clientRepository, never()).save(any());

    }

}
