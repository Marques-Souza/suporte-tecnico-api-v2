package com.github.marquessouza.suportetecnico.infrastructure.persistence.repository;

import com.github.marquessouza.suportetecnico.infrastructure.persistence.entity.TechnicianEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TechnicianJpaRepository extends JpaRepository<TechnicianEntity, UUID> {
    Optional<TechnicianEntity> findByCpf(String cpf);
}
