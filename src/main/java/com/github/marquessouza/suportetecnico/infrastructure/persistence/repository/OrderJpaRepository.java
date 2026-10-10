package com.github.marquessouza.suportetecnico.infrastructure.persistence.repository;

import com.github.marquessouza.suportetecnico.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
}
