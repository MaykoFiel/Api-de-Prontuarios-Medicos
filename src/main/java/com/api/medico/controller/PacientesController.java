package com.api.medico.controller;

import com.api.medico.dto.DadosCadastroPaciente;
import com.api.medico.dto.DadosDetalhamentoPaciente;
import com.api.medico.repository.PacienteRepository;
import com.api.medico.service.PacientesService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
public class PacientesController {

    private final PacientesService pacientesService;

    public PacientesController(PacientesService pacienteService) {
        this.pacientesService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrarPaciente(
            @RequestBody @Valid DadosCadastroPaciente dados,
            UriComponentsBuilder uriBuilder) {

        DadosDetalhamentoPaciente pacienteSalvo = pacientesService.cadstroPaciente(dados);

        URI uri = uriBuilder.path("pacientes/{id}").buildAndExpand(pacienteSalvo.id()).toUri();

        return ResponseEntity.created(uri).body(pacienteSalvo);

    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoPaciente>> listar(
         @ParameterObject @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {

        var page = pacientesService.listaPaciente(paginacao);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPaciente> detalhar(@PathVariable Long id) {
        var paciente = pacientesService.detalharPaciente(id);
        return ResponseEntity.ok(paciente);
    }

    @PutMapping
    public ResponseEntity<DadosDetalhamentoPaciente> atualizarPaciente(@RequestBody @Valid DadosDetalhamentoPaciente dados) {

        var pacienteAtualizado = pacientesService.atualizar(dados);
        return ResponseEntity.ok(pacienteAtualizado);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> removerPaciente(@PathVariable Long id) {
        pacientesService.removerPaciente(id);
        return ResponseEntity.noContent().build();
    }
}