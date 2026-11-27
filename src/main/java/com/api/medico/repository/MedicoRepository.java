package com.api.medico.repository;

import com.api.medico.model.Consulta;
import com.api.medico.model.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

 Page<Medico> findAllByAtivoTrue(Pageable paginacao);

}
