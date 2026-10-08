package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.TechnicianResponse;
import com.github.marquessouza.suportetecnico.domain.exception.TechnicianNotFoundException;
import com.github.marquessouza.suportetecnico.domain.model.Technician;
import com.github.marquessouza.suportetecnico.domain.repository.TechnicianRepository;

import java.util.UUID;

public class FindTechnicianByIdUseCase {

    private final TechnicianRepository technicianRepository;

    public FindTechnicianByIdUseCase(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    public TechnicianResponse execute(UUID id){
        Technician technician = technicianRepository.findById(id)
                .orElseThrow(TechnicianNotFoundException::new);
        return TechnicianResponse.from(technician);
    }
}
