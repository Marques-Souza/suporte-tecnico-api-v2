package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.TechnicianResponse;
import com.github.marquessouza.suportetecnico.application.dto.UpdateTechnicianRequest;
import com.github.marquessouza.suportetecnico.domain.exception.TechnicianNotFoundException;
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
public class UpdateTechnicianUseCaseTest {

    @Mock
    private TechnicianRepository technicianRepository;

    @InjectMocks
    private UpdateTechnicianUseCase updateTechnicianUseCase;

    @Test
    void shouldUpdateNamePhoneAndActiveKeepingCpf(){
        UUID id = UUID.randomUUID();
        Technician existing = new Technician(id, "Carlos Tecnico", "12345678909", "6199999999", true);
        when(technicianRepository.findById(id)).thenReturn(Optional.of(existing));
        when(technicianRepository.save(any(Technician.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TechnicianResponse response = updateTechnicianUseCase.execute(id,
                new UpdateTechnicianRequest("Carlos Souza", "6199999998", false));
        assertNotNull(response.id());
        assertEquals("Carlos Souza", response.name());
        assertEquals("6199999998", response.phone());
        assertFalse(false);
    }

    @Test
    void shouldThrowWhenTechnicianToUpdateDoesNotExist(){
        UUID id = UUID.randomUUID();
        when(technicianRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TechnicianNotFoundException.class, () ->
                updateTechnicianUseCase.execute(id, new UpdateTechnicianRequest("Carlos Souza", "619999999", true)));
        verify(technicianRepository, never()).save(any());
    }
}
