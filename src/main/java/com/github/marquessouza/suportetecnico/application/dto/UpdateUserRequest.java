package com.github.marquessouza.suportetecnico.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(

        @NotBlank
        @Size(min = 3, max = 100)
        String name,

        @NotBlank
        @Email
        @Size(max = 150)
        String email

) {
}
