package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.TechnicianResponse;
import com.github.marquessouza.suportetecnico.domain.model.Technician;
import com.github.marquessouza.suportetecnico.domain.repository.TechnicianRepository;

import java.util.List;

public class ListTechniciansUseCase {

    private final TechnicianRepository technicianRepository;

    public ListTechniciansUseCase(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    public List<TechnicianResponse> execute(){
        return technicianRepository.findAll().stream()
                .map(TechnicianResponse::from)
                .toList();
    }
}
