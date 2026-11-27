package com.api.medico.repository;

import com.api.medico.model.Pacientes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository  extends JpaRepository <Pacientes,  Long>{
    Page<Pacientes> findAllByAtivoTrue(Pageable paginacao);

}
