package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.CreateOrderRequest;
import com.github.marquessouza.suportetecnico.application.dto.OrderResponse;
import com.github.marquessouza.suportetecnico.domain.exception.ClientNotFoundException;
import com.github.marquessouza.suportetecnico.domain.exception.TechnicianInactiveException;
import com.github.marquessouza.suportetecnico.domain.exception.TechnicianNotFoundException;
import com.github.marquessouza.suportetecnico.domain.model.Order;
import com.github.marquessouza.suportetecnico.domain.model.Technician;
import com.github.marquessouza.suportetecnico.domain.repository.ClientRepository;
import com.github.marquessouza.suportetecnico.domain.repository.OrderRepository;
import com.github.marquessouza.suportetecnico.domain.repository.TechnicianRepository;

import java.util.UUID;

public class CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final TechnicianRepository technicianRepository;

    public CreateOrderUseCase(OrderRepository orderRepository, ClientRepository clientRepository, TechnicianRepository technicianRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.technicianRepository = technicianRepository;
    }

    public OrderResponse execute(CreateOrderRequest request){
        ensureClientExists(request.clientId());
        ensureTechnicianExists(request.technicianId());

        Order order = Order.open(request.clientId(), request.technicianId(), request.description());
        return OrderResponse.from(orderRepository.save(order));
    }

    private void ensureClientExists(UUID clientId){
        clientRepository.findById(clientId).orElseThrow(ClientNotFoundException::new);
    }

    private void ensureTechnicianExists(UUID technicianId){
        Technician technician = technicianRepository.findById(technicianId)
                .orElseThrow(TechnicianNotFoundException::new);

        if (!technician.isActive()){
            throw new TechnicianInactiveException();
        }
    }
}
