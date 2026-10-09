package com.github.marquessouza.suportetecnico.domain.model;

import com.github.marquessouza.suportetecnico.domain.exception.InvalidOrderStatusTransitionException;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {

    private final UUID id ;
    private final String description;
    private final OrderStatus status;
    private final UUID clientId;
    private final UUID technicianId;
    private final LocalDateTime createdAt;


    public Order(UUID id, String description, OrderStatus status, UUID clientId, UUID technicianId, LocalDateTime createdAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.clientId = clientId;
        this.technicianId = technicianId;
        this.createdAt = createdAt;
    }

    public static Order open(UUID clientId, UUID technicianId, String description){
        return new Order(UUID.randomUUID(), description, OrderStatus.OPEN,
                clientId, technicianId, LocalDateTime.now());
    }

    public Order changeStatusTo(OrderStatus newStatus){
        if (!status.canTransitionTo(newStatus)){
            throw new InvalidOrderStatusTransitionException(status, newStatus);
        }
        return new Order(id, description, status, clientId, technicianId, LocalDateTime.now());
    }

    public UUID getId(){return id;}
    public  String getDescription(){return description;}
    public OrderStatus getStatus(){return status;}
    public UUID getClientId(){return clientId;}
    public UUID getTechnicianId(){return technicianId;}
    public LocalDateTime getCreatedAt(){return createdAt;}

}
