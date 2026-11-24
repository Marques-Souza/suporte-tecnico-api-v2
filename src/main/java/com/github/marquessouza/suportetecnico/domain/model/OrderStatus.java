package com.github.marquessouza.suportetecnico.domain.model;

public enum OrderStatus {
    OPEN,
    IN_PROGRESS,
    CLOSED;

    public boolean canTransitionTo(OrderStatus next){
        return switch (this){
            case OPEN -> next == IN_PROGRESS;
            case IN_PROGRESS -> next == CLOSED;
            case CLOSED -> false;
        };
    }
}
