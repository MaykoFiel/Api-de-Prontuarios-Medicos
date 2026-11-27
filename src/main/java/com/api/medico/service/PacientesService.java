package com.api.medico.service;

import com.api.medico.dto.DadosCadastroPaciente;
import com.api.medico.dto.DadosDetalhamentoPaciente;
import com.api.medico.model.Pacientes;
import com.api.medico.model.TipoPerfil;
import com.api.medico.model.Usuario;
import com.api.medico.repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.parser.Entity;

@Service
public class PacientesService {

    private  final PacienteRepository pacienteRepository;
    private final PasswordEncoder passwordEncoder;

    public PacientesService(PacienteRepository pacienteRepository, PasswordEncoder passwordEncoder) {
        this.pacienteRepository = pacienteRepository;
        this.passwordEncoder = passwordEncoder;

    }
    @Transactional
    public DadosDetalhamentoPaciente cadstroPaciente(DadosCadastroPaciente dados) {

        Usuario usuario = new Usuario();
        usuario.setEmail(dados.email());
        usuario.setNome(dados.nome());
        usuario.setSenha(passwordEncoder.encode(dados.senha()));
        usuario.setRole(TipoPerfil.ROLE_PACIENTE);

        Pacientes pacientes = new Pacientes();
        pacientes.setNome(dados.nome());
        pacientes.setCpf(dados.cpf());
        pacientes.setTelefone(dados.telefone());

        pacientes.setUsuario(usuario);

        pacienteRepository.save(pacientes);
        return new DadosDetalhamentoPaciente(pacientes);
    }

    public Page<DadosDetalhamentoPaciente> listaPaciente(Pageable pageable) {
        return pacienteRepository.findAll(pageable).map(DadosDetalhamentoPaciente::new);
    }
    @Transactional
    public DadosDetalhamentoPaciente atualizar (DadosDetalhamentoPaciente dados) {
         var paciente = pacienteRepository.getReferenceById(dados.id());

         return new  DadosDetalhamentoPaciente(paciente);

    }

   @Transactional
    public void removerPaciente(@PathVariable Long id) {
       var paciente = pacienteRepository.getReferenceById(id);
       paciente.deletarPaciente();
    }

    public DadosDetalhamentoPaciente detalharPaciente(Long id) {
        var paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontardo com o ID" + id));

        return new DadosDetalhamentoPaciente(paciente);
    }
}
