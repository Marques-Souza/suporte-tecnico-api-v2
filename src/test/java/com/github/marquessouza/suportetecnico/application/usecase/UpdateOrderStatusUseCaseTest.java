package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.OrderResponse;
import com.github.marquessouza.suportetecnico.application.dto.UpdateOrderStatusRequest;
import com.github.marquessouza.suportetecnico.domain.exception.InvalidOrderStatusTransitionException;
import com.github.marquessouza.suportetecnico.domain.exception.OrderNotFoundException;
import com.github.marquessouza.suportetecnico.domain.model.Order;
import com.github.marquessouza.suportetecnico.domain.model.OrderStatus;
import com.github.marquessouza.suportetecnico.domain.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateOrderStatusUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private UpdateOrderStatusUseCase updateOrderStatusUseCase;

    private Order orderWithStatus(UUID id, OrderStatus status) {
        return new Order(id, "Computador nao liga", status,
                UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now());
    }

    @Test
    void shouldUpdateStatusWhenTransitionIsAllowed() {
        UUID id = UUID.randomUUID();
        when(orderRepository.findById(id)).thenReturn(Optional.of(orderWithStatus(id, OrderStatus.OPEN)));
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponse response = updateOrderStatusUseCase.execute(
                id, new UpdateOrderStatusRequest(OrderStatus.IN_PROGRESS));

        assertEquals(OrderStatus.IN_PROGRESS, response.status());
    }

    @Test
    void shouldThrowWhenTransitionIsNotAllowed() {
        UUID id = UUID.randomUUID();
        when(orderRepository.findById(id)).thenReturn(Optional.of(orderWithStatus(id, OrderStatus.CLOSED)));

        assertThrows(InvalidOrderStatusTransitionException.class, () ->
                updateOrderStatusUseCase.execute(id, new UpdateOrderStatusRequest(OrderStatus.OPEN)));

        verify(orderRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenOrderDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () ->
                updateOrderStatusUseCase.execute(id, new UpdateOrderStatusRequest(OrderStatus.IN_PROGRESS)));

        verify(orderRepository, never()).save(any());
    }
}