package com.github.marquessouza.suportetecnico.application.dto;

public record CreateClientRequest(
        String name,
        String cpf,
        String phone
) {
}
