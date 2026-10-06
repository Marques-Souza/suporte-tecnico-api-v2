package com.github.marquessouza.suportetecnico.infrastructure.persistence.repository;

import com.github.marquessouza.suportetecnico.domain.model.Client;
import com.github.marquessouza.suportetecnico.domain.repository.ClientRepository;
import com.github.marquessouza.suportetecnico.infrastructure.persistence.entity.ClientEntity;
import com.github.marquessouza.suportetecnico.infrastructure.persistence.mapper.ClientEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private final ClientJpaRepository clientJpaRepository;
    private final ClientEntityMapper mapper;

    public ClientRepositoryImpl(ClientJpaRepository clientJpaRepository, ClientEntityMapper mapper) {
        this.clientJpaRepository = clientJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Client save(Client client){
        ClientEntity savedEntity = clientJpaRepository.save(mapper.toEntity(client));
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Client> findById(UUID id){
        return clientJpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Client> findByCpf(String cpf){
        return clientJpaRepository.findByCpf(cpf).map(mapper::toDomain);
    }

    @Override
    public List<Client> findAll(){
       return clientJpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

}
