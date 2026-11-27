package com.api.medico.dto;

import com.api.medico.model.Medico;

public record DadosListaMedica(

        Long id,
        String nome,
        String email,
        String crm,
        String especialidade,
        String telefone
) {

    public DadosListaMedica(Medico medico){
        this(
                medico.getId(),
                medico.getNome(),
                medico.getUsuario() != null ? medico.getUsuario().getEmail() : null,
                medico.getCrm(),
                medico.getEspecialidade(),
                medico.getTelefone()
        );
    }
}
