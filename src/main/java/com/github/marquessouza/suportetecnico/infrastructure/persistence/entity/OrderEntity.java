package com.github.marquessouza.suportetecnico.infrastructure.persistence.entity;

import com.github.marquessouza.suportetecnico.domain.model.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderEntity {

    @Id
    private UUID id;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatus status;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "technician_id", nullable = false)
    private UUID technicianId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
