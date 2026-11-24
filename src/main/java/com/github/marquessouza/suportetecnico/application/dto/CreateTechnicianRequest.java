package com.github.marquessouza.suportetecnico.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record CreateTechnicianRequest(

        @NotBlank
        @Size(min = 3, max = 100)
        String name,

        @NotBlank
        @Size(min = 11, max = 11)
        @Pattern(regexp = "\\d{11}", message = "CPF must be 11 digits")
        @CPF(message = "Invalid CPF")
        String cpf,

        @NotBlank
        @Size(max = 20)
        String phone

) {
}
