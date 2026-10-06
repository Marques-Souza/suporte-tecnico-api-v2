package com.github.marquessouza.suportetecnico.application.dto;

import java.util.UUID;

public record ClientResponse(
        UUID id,
        String name,
        String cpf,
        String phone
) {
}
