package com.github.marquessouza.suportetecnico.infrastructure.persistence.repository;

import com.github.marquessouza.suportetecnico.domain.model.Technician;
import com.github.marquessouza.suportetecnico.domain.repository.TechnicianRepository;
import com.github.marquessouza.suportetecnico.infrastructure.persistence.entity.TechnicianEntity;
import com.github.marquessouza.suportetecnico.infrastructure.persistence.mapper.TechnicianEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TechnicianRepositoryImpl  implements TechnicianRepository {

    private final TechnicianJpaRepository jpaRepository;
    private final TechnicianEntityMapper mapper;

    public TechnicianRepositoryImpl(TechnicianJpaRepository jpaRepository, TechnicianEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Technician save(Technician technician){
        TechnicianEntity savedEntity = jpaRepository.save(mapper.toEntity(technician));
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Technician> findById(UUID id){
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Technician> findByCpf(String cpf){
        return jpaRepository.findByCpf(cpf).map(mapper::toDomain);
    }

    @Override
    public List<Technician> findAll(){
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

}
