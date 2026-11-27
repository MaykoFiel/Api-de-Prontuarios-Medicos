package com.api.medico.dto;

import com.api.medico.model.Medico;

public record DadosDetalhadamentoMedico(

        Long id,
        String nome,
        String crm,
        String especialidade,
        String email,
        String telefone

) {

    public DadosDetalhadamentoMedico(Medico medico) {
        this(
                medico.getId(),
                medico.getNome(),
                medico.getCrm(),
                medico.getEspecialidade(),
                medico.getUsuario().getEmail(),
                medico.getTelefone()
        );
    }
}


