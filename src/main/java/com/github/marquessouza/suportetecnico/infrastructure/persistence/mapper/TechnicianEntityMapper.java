package com.github.marquessouza.suportetecnico.infrastructure.persistence.mapper;

import com.github.marquessouza.suportetecnico.domain.model.Technician;
import com.github.marquessouza.suportetecnico.infrastructure.persistence.entity.TechnicianEntity;
import org.springframework.stereotype.Component;

@Component
public class TechnicianEntityMapper {

    public TechnicianEntity toEntity(Technician technician) {
        return new TechnicianEntity(
                technician.getId(),
                technician.getName(),
                technician.getCpf(),
                technician.getPhone(),
                technician.isActive()
        );
    }

    public Technician toDomain(TechnicianEntity entity) {
        return new Technician(
                entity.getId(),
                entity.getName(),
                entity.getCpf(),
                entity.getPhone(),
                entity.isActive()

        );

    }

}
