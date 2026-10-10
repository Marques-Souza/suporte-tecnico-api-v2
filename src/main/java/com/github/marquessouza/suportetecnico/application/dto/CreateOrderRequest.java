package com.github.marquessouza.suportetecnico.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateOrderRequest(

        @NotNull
        UUID clientId,

        @NotNull
        UUID technicianId,

        @NotNull
        @Size(max = 500)
        String description
) {

}
