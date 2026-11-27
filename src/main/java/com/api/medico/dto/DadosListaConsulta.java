package com.api.medico.dto;

import com.api.medico.model.Consulta;
import lombok.extern.java.Log;

import java.time.LocalDateTime;

public record DadosListaConsulta(

        Long id,
        Long idMedico,
        String nomeMedico,
        Long idPaciente,
        LocalDateTime data

) {

    public DadosListaConsulta(Consulta consulta) {
        this(
                consulta.getId(),
                consulta.getMedico().getId(),
                consulta.getMedico().getNome(),
                consulta.getPaciente().getId(),
                consulta.getData()
        );
    }
}
