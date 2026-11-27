package com.api.medico.dto;

import com.api.medico.model.Pacientes;

public record DadosDetalhamentoPaciente(

        Long id,

        String nome,

        String cpf,

        String telefone,

        String email


) {
    public DadosDetalhamentoPaciente(Pacientes pacientes) {
        this(
                pacientes.getId(),
                pacientes.getNome(),
                pacientes.getCpf(),
                pacientes.getTelefone(),
                pacientes.getUsuario().getEmail()

        );
    }
}
