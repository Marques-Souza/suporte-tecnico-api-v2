package com.github.marquessouza.suportetecnico.domain.exception;

import com.github.marquessouza.suportetecnico.domain.model.OrderStatus;

public class InvalidOrderStatusTransitionException extends RuntimeException {
    public InvalidOrderStatusTransitionException(OrderStatus from, OrderStatus to) {

        super("Cannot change order status from " + from + " to " + to);
    }
}
