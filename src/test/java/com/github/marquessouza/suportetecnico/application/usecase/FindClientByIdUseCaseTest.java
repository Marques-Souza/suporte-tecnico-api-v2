package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.ClientResponse;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindClientByIdUseCaseTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private FindClientByIdUseCase findClientByIdUseCase;

    @Test
    void shouldReturnClientWhenIdExists(){
        UUID id = UUID.randomUUID();
        Client client = new Client(id, "John Cleber", "12345678909", "61999999999");
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        ClientResponse response = findClientByIdUseCase.execute(id);

        assertEquals(id, response.id());
        assertEquals("John Cleber", response.name());
        assertEquals("12345678909", response.cpf());
        assertEquals("61999999999", response.phone());
    }

    @Test
    void shouldThrowWhenClientDoesNotExist(){
        UUID id = UUID.randomUUID();
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> findClientByIdUseCase.execute(id));

    }
}
