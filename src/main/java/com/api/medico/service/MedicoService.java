package com.api.medico.service;

import com.api.medico.dto.DadosCadastroMedico;
import com.api.medico.dto.DadosDetalhadamentoMedico;
import com.api.medico.model.Medico;
import com.api.medico.model.TipoPerfil;
import com.api.medico.model.Usuario;
import com.api.medico.repository.MedicoRepository;
import com.api.medico.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    private final MedicoRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public MedicoService(MedicoRepository repository, PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public DadosDetalhadamentoMedico cadastroMedico(DadosCadastroMedico dados) {
        var usuario = new Usuario();
        usuario.setNome(dados.nome());
        usuario.setEmail(dados.email());
        usuario.setSenha(passwordEncoder.encode(dados.senha()));
        usuario.setRole(TipoPerfil.ROLE_MEDICO);
       usuarioRepository.save(usuario);


        var medico = new Medico();
        medico.setNome(dados.nome());
        medico.setCrm(dados.crm());
        medico.setEspecialidade(dados.especialidade());
        medico.setTelefone(dados.telefone());
        medico.setUsuario(usuario);
        repository.save(medico);

        return new DadosDetalhadamentoMedico(medico);

    }

    public Page<DadosDetalhadamentoMedico> listaMedico(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).map(DadosDetalhadamentoMedico::new);
    }

    @Transactional
    public DadosDetalhadamentoMedico atualizar (DadosDetalhadamentoMedico dados) {
        var medico = repository.getReferenceById(dados.id());

        medico.atualizarInformacoes(dados);

        return new DadosDetalhadamentoMedico(medico);
    }

    @Transactional
    public void excluir(Long id) {
        var medico = repository.getReferenceById(id);
        medico.inativar();
    }

    public DadosDetalhadamentoMedico detalharMedico(Long id){
        var medico = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado com o ID" + id));

                return new DadosDetalhadamentoMedico(medico);

    }

}
