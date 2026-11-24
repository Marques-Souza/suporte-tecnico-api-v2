package com.github.marquessouza.suportetecnico.domain.model;

import com.github.marquessouza.suportetecnico.domain.exception.InvalidOrderStatusTransitionException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

    private Order newOrder() {
        return Order.open(UUID.randomUUID(), UUID.randomUUID(), "Computador não liga");
    }

    @Test
    void shouldOpenOrderWithOpenStatus() {
        Order order = newOrder();

        assertNotNull(order.getId());
        assertNotNull(order.getCreatedAt());
        assertEquals(OrderStatus.OPEN, order.getStatus());
    }

    @Test
    void shouldMoveForwardThroughAllowedStatuses() {
        Order inProgress = newOrder().changeStatusTo(OrderStatus.IN_PROGRESS);
        Order closed = inProgress.changeStatusTo(OrderStatus.CLOSED);

        assertEquals(OrderStatus.IN_PROGRESS, inProgress.getStatus());
        assertEquals(OrderStatus.CLOSED, closed.getStatus());
    }

    @Test
    void shouldNotSkipFromOpenToClosed() {
        Order order = newOrder();

        assertThrows(InvalidOrderStatusTransitionException.class, () -> order.changeStatusTo(
                OrderStatus.CLOSED));
    }

    @Test
    void shouldNotReopenAClosedOrder() {
        Order closed = newOrder()
                .changeStatusTo(OrderStatus.IN_PROGRESS)
                .changeStatusTo(OrderStatus.CLOSED);
        assertThrows(InvalidOrderStatusTransitionException.class, () -> closed.changeStatusTo(OrderStatus.OPEN));

    }

    @Test
    void shouldKeepOriginalOrderUnchangedAfterStatusChange() {
        Order order = newOrder();

        order.changeStatusTo(OrderStatus.IN_PROGRESS);

        assertEquals(OrderStatus.OPEN, order.getStatus());
    }

}
