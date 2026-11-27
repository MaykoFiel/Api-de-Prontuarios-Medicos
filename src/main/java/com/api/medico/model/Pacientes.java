package com.api.medico.model;

import com.api.medico.dto.DadaosAtulizacaoPaciente;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pacientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

    private String telefone;

    private Boolean ativo = true;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id" , referencedColumnName = "id")
    private Usuario usuario;

    public void atualizarInformaçoes(DadaosAtulizacaoPaciente dados) {
        if (dados.nome() != null && !dados.nome().trim().isEmpty()) {
            this.nome = dados.nome();
        }
        if (dados.telefone() != null && !dados.telefone().trim().isEmpty()) {
            this.telefone = dados.telefone();
        }
    }
    public void deletarPaciente() {
        this.ativo= false;
    }

}
