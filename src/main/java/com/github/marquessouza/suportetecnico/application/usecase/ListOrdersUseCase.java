package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.OrderResponse;
import com.github.marquessouza.suportetecnico.domain.repository.OrderRepository;

import java.util.List;

public class ListOrdersUseCase {

    private final OrderRepository orderRepository;

    public ListOrdersUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderResponse> execute(){
        return orderRepository.findAll().stream()
                .map(OrderResponse::from)
                .toList();
    }
}
