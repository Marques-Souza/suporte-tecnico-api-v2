package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.ClientResponse;
import com.github.marquessouza.suportetecnico.domain.exception.ClientNotFoundException;
import com.github.marquessouza.suportetecnico.domain.model.Client;
import com.github.marquessouza.suportetecnico.domain.repository.ClientRepository;

import java.util.UUID;

public class FindClientByIdUseCase {

    private final ClientRepository clientRepository;

    public FindClientByIdUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientResponse execute(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(ClientNotFoundException::new);
        return ClientResponse.from(client);
    }
}
