package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.ClientResponse;
import com.github.marquessouza.suportetecnico.application.dto.UpdateClientRequest;
import com.github.marquessouza.suportetecnico.domain.exception.ClientNotFoundException;
import com.github.marquessouza.suportetecnico.domain.model.Client;
import com.github.marquessouza.suportetecnico.domain.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class UpdateClientUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private UpdateClientUseCase updateClientUseCase;

    @Test
    void shouldUpdateNameAndPhoneKeepingCpf(){
        UUID id = UUID.randomUUID();
        Client existingClient = new Client(id, "John Cleber", "12345678909","61999999999");
        when(clientRepository.findById(id)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(any(Client.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ClientResponse response = updateClientUseCase.execute(id, new UpdateClientRequest( "John Vargas", "6199999998"));
        assertEquals(id, response.id());
        assertEquals("John Vargas", response.name());
        assertEquals("12345678909", response.cpf());
        assertEquals("6199999998", response.phone());
    }

    @Test
    void shouldThrowWhenClientToUpdateDoesNotExist(){
        UUID id = UUID.randomUUID();
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> updateClientUseCase
                .execute(id, new UpdateClientRequest("John Vargas", "6199999998")));
        verify(clientRepository, never()).save(any());
    }
}
