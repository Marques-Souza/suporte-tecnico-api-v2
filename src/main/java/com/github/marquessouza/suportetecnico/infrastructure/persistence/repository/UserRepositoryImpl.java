package com.github.marquessouza.suportetecnico.infrastructure.persistence.repository;

import com.github.marquessouza.suportetecnico.domain.model.User;
import com.github.marquessouza.suportetecnico.domain.repository.UserRepository;
import com.github.marquessouza.suportetecnico.infrastructure.persistence.entity.UserEntity;
import com.github.marquessouza.suportetecnico.infrastructure.persistence.mapper.UserEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserEntityMapper mapper;

    public UserRepositoryImpl(UserJpaRepository jpaRepository, UserEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user){
        UserEntity savedEntity = jpaRepository.save(mapper.toEntity(user));
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(UUID id){
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public  Optional<User> findByEmail(String email){
       return jpaRepository.findByEmail(email)
               .map(mapper::toDomain);
    }

    @Override
    public List<User> findAll(){
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

}
