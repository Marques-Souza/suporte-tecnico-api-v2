package com.github.marquessouza.suportetecnico.domain.repository;

import com.github.marquessouza.suportetecnico.domain.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {

    Client save(Client client);

    Optional<Client> findById(UUID id);

    Optional<Client> findByCpf(String cpf);

    List<Client> findAll();
}
