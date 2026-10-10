package com.github.marquessouza.suportetecnico.infrastructure.config;

import com.github.marquessouza.suportetecnico.application.dto.UpdateOrderStatusRequest;
import com.github.marquessouza.suportetecnico.application.usecase.*;
import com.github.marquessouza.suportetecnico.domain.repository.ClientRepository;
import com.github.marquessouza.suportetecnico.domain.repository.OrderRepository;
import com.github.marquessouza.suportetecnico.domain.repository.TechnicianRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateClientUseCase createClientUseCase(ClientRepository clientRepository){
        return new CreateClientUseCase(clientRepository);
    }

    @Bean
    public FindClientByIdUseCase findClientByIdUseCase(ClientRepository clientRepository){
        return new FindClientByIdUseCase(clientRepository);
    }

    @Bean
    public ListClientsUseCase listClientsUseCase(ClientRepository clientRepository){
        return new ListClientsUseCase(clientRepository);
    }

    @Bean
    public UpdateClientUseCase updateClientUseCase(ClientRepository clientRepository){
        return new UpdateClientUseCase(clientRepository);
    }

    @Bean
    public CreateTechnicianUseCase createTechnicianUseCase(TechnicianRepository technicianRepository){
        return new CreateTechnicianUseCase(technicianRepository);
    }

    @Bean
    public FindTechnicianByIdUseCase findTechnicianByIdUseCase(TechnicianRepository technicianRepository){
        return new FindTechnicianByIdUseCase(technicianRepository);
    }


    @Bean
    public ListTechniciansUseCase listTechniciansUseCase(TechnicianRepository technicianRepository){
        return new ListTechniciansUseCase(technicianRepository);
    }

    @Bean
    public UpdateTechnicianUseCase updateTechnicianUseCase(TechnicianRepository technicianRepository){
        return new UpdateTechnicianUseCase(technicianRepository);
    }

    @Bean
    public CreateOrderUseCase createOrderUseCase(OrderRepository orderRepository,
                                                 ClientRepository clientRepository,
                                                 TechnicianRepository technicianRepository){
        return new CreateOrderUseCase(orderRepository, clientRepository, technicianRepository);
    }

    @Bean
    public FindOrderByIdUseCase findOrderByIdUseCase(OrderRepository orderRepository){
        return new FindOrderByIdUseCase(orderRepository);
    }

    @Bean
    public ListOrdersUseCase listOrdersUseCase(OrderRepository orderRepository){
        return new ListOrdersUseCase(orderRepository);
    }

    public UpdateOrderStatusUseCase updateOrderStatusUseCase(OrderRepository orderRepository){
        return new UpdateOrderStatusUseCase(orderRepository);
    }
}
