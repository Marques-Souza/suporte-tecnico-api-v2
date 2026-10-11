package com.github.marquessouza.suportetecnico.infrastructure.persistence.mapper;

import com.github.marquessouza.suportetecnico.domain.model.User;
import com.github.marquessouza.suportetecnico.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public UserEntity toEntity(User user){
        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getRole()
        );
    }

    public User toDomain(UserEntity entity){
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getRole()
        );
    }
}
