package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.CreateTechnicianRequest;
import com.github.marquessouza.suportetecnico.application.dto.TechnicianResponse;
import com.github.marquessouza.suportetecnico.application.dto.UpdateTechnicianRequest;
import com.github.marquessouza.suportetecnico.domain.exception.TechnicianNotFoundException;
import com.github.marquessouza.suportetecnico.domain.model.Technician;
import com.github.marquessouza.suportetecnico.domain.repository.TechnicianRepository;

import java.util.UUID;

public class UpdateTechnicianUseCase {

    private final TechnicianRepository technicianRepository;

    public UpdateTechnicianUseCase(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }


    public TechnicianResponse execute(UUID id, UpdateTechnicianRequest request){
        Technician existing = technicianRepository.findById(id)
                .orElseThrow(TechnicianNotFoundException::new);


        Technician updated = new Technician(
                existing.getId(),
                request.name(),
                existing.getCpf(),
                request.phone(),
                request.active()

        );

        return TechnicianResponse.from(technicianRepository.save(updated));
    }
}
