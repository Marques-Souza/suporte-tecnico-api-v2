package com.github.marquessouza.suportetecnico.application.usecase;


import com.github.marquessouza.suportetecnico.application.dto.CreateTechnicianRequest;
import com.github.marquessouza.suportetecnico.application.dto.TechnicianResponse;
import com.github.marquessouza.suportetecnico.domain.exception.TechnicianAlreadyExistsException;
import com.github.marquessouza.suportetecnico.domain.model.Technician;
import com.github.marquessouza.suportetecnico.domain.repository.TechnicianRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateTechnicianUseCaseTest {

    @Mock
    private TechnicianRepository technicianRepository;

    @InjectMocks
    private CreateTechnicianUseCase createTechnicianUseCase;


    @Test
    void shouldCreateActiveTechnicianWhenCpfIsNotRegistered(){
        when(technicianRepository.findByCpf("12345678909")).thenReturn(Optional.empty());
        when(technicianRepository.save(any(Technician.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TechnicianResponse response = createTechnicianUseCase.execute(
                new CreateTechnicianRequest("Carlos Tecnico", "12345678909", "12345678"));
        assertNotNull(response.id());
        assertTrue(response.active());
        verify(technicianRepository).save(any(Technician.class));
    }

    @Test
    void shouldThrowWhenCpfIsAlreadyRegistered(){
        Technician existing = new Technician(
                UUID.randomUUID(), "Carlos Tecnico", "12345678909", "619999999", true);
        when(technicianRepository.findByCpf("12345678909")).thenReturn(Optional.of(existing));

        assertThrows(TechnicianAlreadyExistsException.class, () ->
                createTechnicianUseCase.execute(
                        new CreateTechnicianRequest("Outro tecnico", "12345678909", "6199999998")));

        verify(technicianRepository, never()).save(any());
    }

}
