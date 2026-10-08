package com.github.marquessouza.suportetecnico.application.usecase;

import com.github.marquessouza.suportetecnico.application.dto.CreateTechnicianRequest;
import com.github.marquessouza.suportetecnico.application.dto.TechnicianResponse;
import com.github.marquessouza.suportetecnico.domain.exception.TechnicianAlreadyExistsException;
import com.github.marquessouza.suportetecnico.domain.model.Technician;
import com.github.marquessouza.suportetecnico.domain.repository.TechnicianRepository;

import java.util.UUID;

public class CreateTechnicianUseCase {

    private final TechnicianRepository technicianRepository;

    public CreateTechnicianUseCase(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    public TechnicianResponse execute(CreateTechnicianRequest request){
       ensureCpfIsNotRegistered(request.cpf());

        Technician technician = new Technician(
                UUID.randomUUID(),
                request.name(),
                request.cpf(),
                request.phone(),
                true
        );

        return TechnicianResponse.from(technicianRepository.save(technician));

    }



    private void ensureCpfIsNotRegistered(String cpf){
        if (technicianRepository.findByCpf(cpf).isPresent()){
            throw new TechnicianAlreadyExistsException();
        }

    }
}
