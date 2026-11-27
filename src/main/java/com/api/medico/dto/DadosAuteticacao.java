package com.api.medico.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAuteticacao(

        @NotBlank @Email String email,
        @NotBlank String senha
) {
}
