package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.TechnicianResponse;
import com.github.marquessouza.suportetecnico.domain.model.Technician;
import com.github.marquessouza.suportetecnico.domain.repository.TechnicianRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@ExtendWith(MockitoExtension.class)
public class ListTechniciansUseCaseTest {

    @Mock
    private  TechnicianRepository technicianRepository;

    @InjectMocks
    private  ListTechniciansUseCase listTechniciansUseCase;


    @Test
    void shouldReturnAllTechnicians(){
        when(technicianRepository.findAll()).thenReturn(List.of(
        new Technician(UUID.randomUUID(),"Carlos Tecnico", "12345678909","6199999999", true),
        new Technician(UUID.randomUUID(),"Cleber Tecnico", "12345678901","6199999998", false)));

        List<TechnicianResponse> responses = listTechniciansUseCase.execute();

        assertEquals(2, responses.size());
        assertEquals("Carlos Tecnico", responses.get(0).name());
        assertEquals("Cleber Tecnico", responses.get(1).name());
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoTechnicians(){
        when(technicianRepository.findAll()).thenReturn(List.of());

        assertTrue(listTechniciansUseCase.execute().isEmpty());
    }
}
