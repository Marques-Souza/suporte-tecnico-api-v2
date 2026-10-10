package com.github.marquessouza.suportetecnico.application.dto;

import com.github.marquessouza.suportetecnico.domain.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequest(

        @NotNull
        OrderStatus status
) {
}
