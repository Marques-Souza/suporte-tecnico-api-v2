package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.TechnicianResponse;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindTechnicianByIdUseCaseTest {

    @Mock
    private TechnicianRepository technicianRepository;

    @InjectMocks
    private FindTechnicianByIdUseCase findTechnicianByIdUseCase;

    @Test
    void shouldReturnTechnicianWhenIdExists(){
        UUID id = UUID.randomUUID();
        Technician technician = new Technician(id, "Carlos Tecnico", "12345678909","619999999",true);
        when(technicianRepository.findById(id)).thenReturn(Optional.of(technician));

        TechnicianResponse response = findTechnicianByIdUseCase.execute(id);
        assertEquals(id, response.id());
        assertEquals("Carlos Tecnico", response.name());

    }

    @Test
    void shouldThrowWhenTechnicianDoesNotExist(){
        UUID id =UUID.randomUUID();
        when(technicianRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TechnicianNotFoundException.class,() ->
                findTechnicianByIdUseCase.execute(id));

    }
}
