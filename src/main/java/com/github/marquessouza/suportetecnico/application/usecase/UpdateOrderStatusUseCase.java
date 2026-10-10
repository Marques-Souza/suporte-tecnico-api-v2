package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.OrderResponse;
import com.github.marquessouza.suportetecnico.application.dto.UpdateOrderStatusRequest;
import com.github.marquessouza.suportetecnico.domain.exception.OrderNotFoundException;
import com.github.marquessouza.suportetecnico.domain.model.Order;
import com.github.marquessouza.suportetecnico.domain.repository.OrderRepository;

import java.util.UUID;

public class UpdateOrderStatusUseCase {

    private final OrderRepository orderRepository;

    public UpdateOrderStatusUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponse execute(UUID id, UpdateOrderStatusRequest request){
        Order order = orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);

        Order updatedOrder = order.changeStatusTo(request.status());
        return OrderResponse.from(orderRepository.save(updatedOrder));
    }
}
