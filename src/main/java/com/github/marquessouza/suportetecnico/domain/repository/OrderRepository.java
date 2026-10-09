package com.github.marquessouza.suportetecnico.domain.repository;

import com.github.marquessouza.suportetecnico.domain.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(UUID id);

    List<Order> findAll();
}
