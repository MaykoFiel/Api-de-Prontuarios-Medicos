package com.api.medico.dto;

import jakarta.validation.constraints.NotNull;

public record DadaosAtulizacaoPaciente(
        @NotNull Long id,
        String nome,
        String telefone
) {
}
