package com.api.medico.model;

import com.api.medico.dto.DadosDetalhadamentoMedico;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicos")
@Getter
@Setter

@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true,nullable = false)
    private String crm;

    @Column(nullable = false)
    private String especialidade;

    @Column(nullable = false)
    private String telefone;

    private Boolean ativo = true;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    public void atualizarInformacoes(DadosDetalhadamentoMedico dados) {
        if (dados.nome() != null && !dados.nome().trim().isEmpty()) {
            this.nome = dados.nome();
        }
        if (dados.telefone() != null && !dados.telefone().trim().isEmpty()) {
            this.telefone = dados.telefone();
        }
    }

    public Medico() {
        this.ativo = true;
    }

    public void inativar() {
        this.ativo = false;
    }

}
