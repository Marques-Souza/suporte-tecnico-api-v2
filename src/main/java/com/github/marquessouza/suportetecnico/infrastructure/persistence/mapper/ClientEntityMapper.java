package com.github.marquessouza.suportetecnico.infrastructure.persistence.mapper;

import com.github.marquessouza.suportetecnico.domain.model.Client;
import com.github.marquessouza.suportetecnico.infrastructure.persistence.entity.ClientEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientEntityMapper {

    public ClientEntity toEntity(Client client){
        return new ClientEntity(client.getId(), client.getName(), client.getCpf(), client.getPhone());
    }

    public Client toDomain(ClientEntity clientEntity){
        return new Client(clientEntity.getId(), clientEntity.getName(), clientEntity.getCpf(), clientEntity.getPhone());

    }
}
