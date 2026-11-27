package com.api.medico.service;


import com.api.medico.model.Consulta;
import com.api.medico.repository.ConsultaRepository;
import com.api.medico.dto.DadosAgendamentoConsulta;
import com.api.medico.dto.DadosCancelamentoConsulta;
import com.api.medico.dto.DadosDetalhamentoConsulta;
import com.api.medico.repository.MedicoRepository;
import com.api.medico.repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaService(
            ConsultaRepository consultaRepository,
            MedicoRepository medicoRepository,
            PacienteRepository pacienteRepository
    ) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        var paciente = pacienteRepository.findById(dados.idPaciente())
                .orElseThrow(() -> new ValidationException("Id do paciente informado não existe."));

        var medico = medicoRepository.findById(dados.idMedico())
                .orElseThrow(() -> new ValidationException("Id do médico informado não existe."));

        if (!Boolean.TRUE.equals(paciente.getAtivo())) {
            throw new ValidationException("Consulta não pode ser agendada com paciente inativo.");
        }

        if (!Boolean.TRUE.equals(medico.getAtivo())) {
            throw new ValidationException("Consulta não pode ser agendada com médico inativo.");
        }

        validarHorarioFuncionamento(dados.data());
        validarAntecedenciaAgendamento(dados.data());

        if (consultaRepository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(),(dados.data()))) {
            throw new ValidationException("Médico já possui outraconsulta agendada nesse mesmo horário.");
        }

        var primeiroHorario = dados.data().withHour(7).withMinute(0);
        var ultimoHorario = dados.data().withHour(18).withMinute(0);
        if (consultaRepository.existsByPacienteIdAndDataAndMotivoCancelamentoIsNull(dados.idPaciente(), primeiroHorario, ultimoHorario)) {
            throw new ValidationException("Paciente já possui uma consulta agendada nesse dia.");
        }

        var consulta = new Consulta(medico, paciente,dados.data());
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    @Transactional
    public void cancelar(DadosCancelamentoConsulta dados) {
        var consulta = consultaRepository.findById(dados.idConsulta())
                .orElseThrow(() -> new ValidationException("Id da consulta informado não existe."));

        var agora = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

        if (diferencaEmHoras < 24) {
            throw new ValidationException("Consulta somente pode ser cancelada com antecedência mínima de 24h.");
        }

        consulta.cancelar(dados.motivo());
    }

    private void validarHorarioFuncionamento(LocalDateTime data) {
        var domingo = data.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAbertura = data.getHour() < 7;
        var depoisDoEncerramento = data.getHour() > 18;

        if (domingo || antesDaAbertura || depoisDoEncerramento) {
            throw new ValidationException("Consulta fora do horário de funcionamento da clínica (Seg a Sáb, das 07:00 às 19:00).");
        }
    }

    private void validarAntecedenciaAgendamento(LocalDateTime data) {
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(agora, data).toMinutes();

        if (diferencaEmMinutos < 30) {
            throw new ValidationException("Consulta deve ser agendada com antecedência mínima de 30 minutos.");
        }
    }

    public Page<DadosDetalhamentoConsulta> listarConsulta (Pageable paginacao) {
        return consultaRepository.findAllByMotivoCancelamentoIsNull(paginacao)
                .map(DadosDetalhamentoConsulta::new);
    }

    public DadosDetalhamentoConsulta detalharConsulta(Long id) {
        var consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com o ID: " + id));

        return new DadosDetalhamentoConsulta(consulta);
    }
}
