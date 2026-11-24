package com.github.marquessouza.suportetecnico.infrastructure.persistence.mapper;

import com.github.marquessouza.suportetecnico.domain.model.Order;
import com.github.marquessouza.suportetecnico.infrastructure.persistence.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderEntityMapper {

    public OrderEntity toEntity(Order order){
        return new OrderEntity(
                order.getId(),
                order.getDescription(),
                order.getStatus(),
                order.getClientId(),
                order.getTechnicianId(),
                order.getCreatedAt()
        );
    }

    public Order toDomain(OrderEntity entity){
        return new Order(
                entity.getId(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getClientId(),
                entity.getTechnicianId(),
                entity.getCreatedAt()
        );
    }
}
