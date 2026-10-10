package com.github.marquessouza.suportetecnico.infrastructure.persistence.repository;



import com.github.marquessouza.suportetecnico.domain.model.Order;
import com.github.marquessouza.suportetecnico.domain.repository.OrderRepository;
import com.github.marquessouza.suportetecnico.infrastructure.persistence.entity.OrderEntity;
import com.github.marquessouza.suportetecnico.infrastructure.persistence.mapper.OrderEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository jpaRepository;
    private final OrderEntityMapper mapper;

    public OrderRepositoryImpl(OrderJpaRepository jpaRepository, OrderEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Order save(Order order){
        OrderEntity savedEntity = jpaRepository.save(mapper.toEntity(order));
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(UUID id){
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    public List<Order> findAll(){
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

}
