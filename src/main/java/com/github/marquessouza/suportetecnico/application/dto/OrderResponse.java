package com.github.marquessouza.suportetecnico.application.dto;

import com.github.marquessouza.suportetecnico.domain.model.Order;
import com.github.marquessouza.suportetecnico.domain.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        String description,
        OrderStatus status,
        UUID clientId,
        UUID technicianId,
        LocalDateTime createdAt
) {

    public static OrderResponse from(Order order){
        return new OrderResponse(
                order.getId(),
                order.getDescription(),
                order.getStatus(),
                order.getClientId(),
                order.getTechnicianId(),
                order.getCreatedAt()
        );
    }
}
