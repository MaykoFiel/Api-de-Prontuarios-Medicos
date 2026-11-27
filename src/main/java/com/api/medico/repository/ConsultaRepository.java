package com.api.medico.repository;

import com.api.medico.dto.MotivoCancelamento;
import com.api.medico.model.Consulta;
import com.api.medico.model.Pacientes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ConsultaRepository  extends JpaRepository<Consulta, Long> {
   boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime data);

   boolean existsByPacienteIdAndDataAndMotivoCancelamentoIsNull(

           Long idPaciente,
           LocalDateTime primeiroHorario,
           LocalDateTime ultimoHorario
   );

   Page<Consulta> findAllByMotivoCancelamentoIsNull(Pageable pageable);
}
