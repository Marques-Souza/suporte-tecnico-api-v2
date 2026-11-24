package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.ClientResponse;
import com.github.marquessouza.suportetecnico.domain.repository.ClientRepository;

import java.util.List;

public class ListClientsUseCase {

    private final ClientRepository clientRepository;

    public ListClientsUseCase(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public List<ClientResponse> execute(){
        return clientRepository.findAll().stream()
                .map(ClientResponse::from)
                .toList();
    }
}
