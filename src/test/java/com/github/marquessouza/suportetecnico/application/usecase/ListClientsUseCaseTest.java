package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.ClientResponse;
import com.github.marquessouza.suportetecnico.domain.model.Client;
import com.github.marquessouza.suportetecnico.domain.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListClientsUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ListClientsUseCase listClientsUseCase;

    @Test
    void shouldReturnAllClients(){
        when(clientRepository.findAll()).thenReturn(List.of(
                new Client(UUID.randomUUID(), "John Cleber", "12345678909", "61999999999"),
                new Client(UUID.randomUUID(), "Jane Doe", "12345678908", "61999999998")));
        List<ClientResponse> responses = listClientsUseCase.execute();
        assertEquals(2, responses.size());
        assertEquals("John Cleber", responses.get(0).name());
        assertEquals("Jane Doe", responses.get(1).name());
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoClients(){
        when(clientRepository.findAll()).thenReturn(List.of());
        assertTrue(listClientsUseCase.execute().isEmpty());
    }
}
