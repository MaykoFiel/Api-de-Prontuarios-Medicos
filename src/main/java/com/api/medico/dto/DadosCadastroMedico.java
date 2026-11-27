package com.api.medico.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroMedico(

       @NotBlank
        String nome,
       @NotBlank
        String crm,
       @NotBlank
        String especialidade,
       @NotBlank @Email
        String email,
       @NotBlank
        String senha,
       @NotBlank
       String telefone

) {}
