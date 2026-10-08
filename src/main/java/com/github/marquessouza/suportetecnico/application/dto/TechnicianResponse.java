package com.github.marquessouza.suportetecnico.application.dto;

import com.github.marquessouza.suportetecnico.domain.model.Technician;

import java.util.UUID;

public record TechnicianResponse(
        UUID id,
        String name,
        String cpf,
        String phone,
        boolean active
) {

    public static TechnicianResponse from (Technician technician){
        return new TechnicianResponse(
                technician.getId(),
                technician.getName(),
                technician.getCpf(),
                technician.getPhone(),
                technician.isActive()
        );

    }
}
