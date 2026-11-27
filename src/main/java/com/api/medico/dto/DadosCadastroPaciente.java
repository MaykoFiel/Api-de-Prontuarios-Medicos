package com.api.medico.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record DadosCadastroPaciente(

        @NotBlank String nome,

        @NotBlank  @CPF String cpf,

        @NotBlank @Pattern(regexp = "\\d{10,11}") String telefone,

        @NotBlank @Email String email,

        @NotBlank String senha

) {
}
