package com.github.marquessouza.suportetecnico.application.dto;

import com.github.marquessouza.suportetecnico.domain.model.Role;
import com.github.marquessouza.suportetecnico.domain.model.User;

import java.util.UUID;

public record UserResponse(

        UUID id,
        String name,
        String email,
        Role role

) {

    public static UserResponse from (User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );

    }
}
