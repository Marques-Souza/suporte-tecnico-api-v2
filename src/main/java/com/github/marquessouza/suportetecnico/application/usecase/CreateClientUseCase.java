package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.ClientResponse;
import com.github.marquessouza.suportetecnico.application.dto.CreateClientRequest;
import com.github.marquessouza.suportetecnico.domain.model.Client;
import com.github.marquessouza.suportetecnico.domain.repository.ClientRepository;

import java.util.UUID;

public class CreateClientUseCase {

    private final ClientRepository clientRepository;


    public CreateClientUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientResponse execute(CreateClientRequest request) {
        Client client = new Client(
                UUID.randomUUID(),
                request.name(),
                request.cpf(),
                request.phone()
        );


        Client savedClient = clientRepository.save(client);
        return new ClientResponse(
                savedClient.getId(),
                savedClient.getName(),
                savedClient.getCpf(),
                savedClient.getPhone()
        );

    }
}
