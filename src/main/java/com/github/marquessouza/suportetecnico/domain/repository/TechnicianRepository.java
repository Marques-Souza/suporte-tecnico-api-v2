package com.github.marquessouza.suportetecnico.domain.repository;

import com.github.marquessouza.suportetecnico.domain.model.Technician;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TechnicianRepository {

    Technician save(Technician technician);

    Optional<Technician> findById(UUID id);

    Optional<Technician> findByCpf(String cpf);

    List<Technician> findAll();
}
