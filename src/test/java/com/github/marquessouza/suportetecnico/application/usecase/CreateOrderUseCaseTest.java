package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.CreateOrderRequest;
import com.github.marquessouza.suportetecnico.application.dto.OrderResponse;
import com.github.marquessouza.suportetecnico.domain.exception.ClientNotFoundException;
import com.github.marquessouza.suportetecnico.domain.exception.TechnicianInactiveException;
import com.github.marquessouza.suportetecnico.domain.exception.TechnicianNotFoundException;
import com.github.marquessouza.suportetecnico.domain.model.Client;
import com.github.marquessouza.suportetecnico.domain.model.Order;
import com.github.marquessouza.suportetecnico.domain.model.OrderStatus;
import com.github.marquessouza.suportetecnico.domain.model.Technician;
import com.github.marquessouza.suportetecnico.domain.repository.ClientRepository;
import com.github.marquessouza.suportetecnico.domain.repository.OrderRepository;
import com.github.marquessouza.suportetecnico.domain.repository.TechnicianRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateOrderUseCaseTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private TechnicianRepository technicianRepository;

    @InjectMocks
    private CreateOrderUseCase createOrderUseCase;

    private final UUID clientId = UUID.randomUUID();
    private final UUID technicianId = UUID.randomUUID();

    private CreateOrderRequest request(){
        return new CreateOrderRequest(clientId, technicianId, "Computador não liga");
    }

    private Client client(){
        return new Client(clientId, "Maria da Silva", "12345678909", "6199999999");
    }

    private Technician technician(boolean active){
        return new Technician(technicianId, "Carlos Tecnico", "98765432100","6199999998", active);
    }

    @Test
    void shouldOpenOrderWhenClientExistsAndTechnicianIsActive() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client()));
        when(technicianRepository.findById(technicianId)).thenReturn(Optional.of(technician(true)));
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponse response = createOrderUseCase.execute(request());

        assertEquals(OrderStatus.OPEN, response.status());
        assertEquals(clientId, response.clientId());
        assertEquals(technicianId, response.technicianId());
    }

    @Test
    void shouldThrowWhenClientDoesNotExist() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class,
                () -> createOrderUseCase.execute(request()));

        verify(orderRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenTechnicianDoesNotExist() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client()));
        when(technicianRepository.findById(technicianId)).thenReturn(Optional.empty());

        assertThrows(TechnicianNotFoundException.class,
                () -> createOrderUseCase.execute(request()));

        verify(orderRepository, never()).save(any());
    }

    @Test
    void shouldThrowWhenTechnicianIsInactive() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client()));
        when(technicianRepository.findById(technicianId)).thenReturn(Optional.of(technician(false)));

        assertThrows(TechnicianInactiveException.class,
                () -> createOrderUseCase.execute(request()));

        verify(orderRepository, never()).save(any());
    }


}
