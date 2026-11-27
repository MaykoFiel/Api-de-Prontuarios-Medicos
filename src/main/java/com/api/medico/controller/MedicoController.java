package com.api.medico.controller;

import com.api.medico.dto.DadosCadastroMedico;
import com.api.medico.dto.DadosDetalhadamentoMedico;
import com.api.medico.service.MedicoService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
@RestController
@RequestMapping("/medicos")
public class MedicoController {

    private MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @PostMapping
    public ResponseEntity<DadosDetalhadamentoMedico> cadastro(
            @RequestBody @Valid DadosCadastroMedico dados,
            UriComponentsBuilder uriBuilder) {

        var medicoDetalhado = medicoService.cadastroMedico(dados);

        URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medicoDetalhado.id()).toUri();
        return ResponseEntity.created(uri).body(medicoDetalhado);

    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhadamentoMedico>> listar(
        @ParameterObject @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {

        var page = medicoService.listaMedico(paginacao);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhadamentoMedico> detalhar(@PathVariable Long id) {
        var medico = medicoService.detalharMedico(id);
        return ResponseEntity.ok(medico);
    }

    @PutMapping
    public ResponseEntity<DadosDetalhadamentoMedico> atualizar(@RequestBody @Valid DadosDetalhadamentoMedico dados){
        var medicoAtualizado = medicoService.atualizar(dados);
        return ResponseEntity.ok(medicoAtualizado);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Long id){
        medicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
