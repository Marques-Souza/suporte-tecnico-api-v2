package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.ClientResponse;
import com.github.marquessouza.suportetecnico.application.dto.UpdateClientRequest;
import com.github.marquessouza.suportetecnico.domain.exception.ClientNotFoundException;
import com.github.marquessouza.suportetecnico.domain.model.Client;
import com.github.marquessouza.suportetecnico.domain.repository.ClientRepository;

import java.util.UUID;

public class UpdateClientUseCase {

    private final ClientRepository clientRepository;

    public UpdateClientUseCase(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public ClientResponse execute(UUID id, UpdateClientRequest request){
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(ClientNotFoundException::new);

        Client updatedClient = new Client(
                existingClient.getId(),
                request.name(),
                existingClient.getCpf(),
                request.phone()
        );

        return ClientResponse.from(clientRepository.save(updatedClient));

    }
}
