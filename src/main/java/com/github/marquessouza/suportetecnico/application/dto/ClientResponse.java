package com.github.marquessouza.suportetecnico.application.dto;

import com.github.marquessouza.suportetecnico.domain.model.Client;

import java.util.UUID;

public record ClientResponse(
        UUID id,
        String name,
        String cpf,
        String phone
) {

    public static ClientResponse from(Client client){
        return new  ClientResponse(
        client.getId(),
        client.getName(),
        client.getCpf(),
        client.getPhone()

        );
    }
}
