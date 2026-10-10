package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.OrderResponse;
import com.github.marquessouza.suportetecnico.domain.exception.OrderNotFoundException;
import com.github.marquessouza.suportetecnico.domain.model.Order;
import com.github.marquessouza.suportetecnico.domain.repository.OrderRepository;

import java.util.UUID;

public class FindOrderByIdUseCase {

    private final OrderRepository orderRepository;

    public FindOrderByIdUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponse execute(UUID id){
        Order order = orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);

        return OrderResponse.from(order);
    }
}
