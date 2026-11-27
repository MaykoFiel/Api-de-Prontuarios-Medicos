package com.api.medico.dto;

import com.api.medico.model.Pacientes;

public record DadosListaPaciente(

        Long id,
        String nome,
        String cpf,
        String telefone
) {

    public DadosListaPaciente(Pacientes pacientes) {
        this(
                pacientes.getId(),
                pacientes.getNome(),
                pacientes.getCpf(),
                pacientes.getTelefone()
        );
    }
}
