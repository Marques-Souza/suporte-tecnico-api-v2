package com.github.marquessouza.suportetecnico.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserPasswordRequest(

        @NotBlank
        @Size(min = 6, max = 100)
        String newPassword

) {
}
