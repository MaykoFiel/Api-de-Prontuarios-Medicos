package com.api.medico.controller;

import com.api.medico.dto.DadosAgendamentoConsulta;
import com.api.medico.dto.DadosCancelamentoConsulta;
import com.api.medico.dto.DadosDetalhamentoConsulta;
import com.api.medico.service.ConsultaService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        var dto = service.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoConsulta>> listar(
      @ParameterObject @PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {
         var page = service.listarConsulta(paginacao);
         return ResponseEntity.ok(page);
        }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoConsulta> detalhar(@PathVariable Long id) {
        var consulta = service.detalharConsulta(id);
        return ResponseEntity.ok(consulta);
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        service.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
