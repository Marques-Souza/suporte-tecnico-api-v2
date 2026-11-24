package com.github.marquessouza.suportetecnico.application.dto;

import com.github.marquessouza.suportetecnico.domain.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

        @NotBlank
        @Size(min = 3, max = 100)
        String name,

        @NotBlank
        @Email
        @Size(max = 150)
        String email,

        @NotBlank
        @Size(min = 6, max = 100)
        String password,

        @NotNull
        Role role
) {
}
