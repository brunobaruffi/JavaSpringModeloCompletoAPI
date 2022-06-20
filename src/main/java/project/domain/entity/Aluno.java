package project.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.enums.StatusAlunos;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="aluno")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @NotEmpty(message = "Campo nome obrigatorio")
    @Column(name="nome", length = 120)
    private String nome;

    @NotNull(message = "Data Obrigatoria")
    @Column(name="dataNas")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dataNas;

    @NotEmpty(message = "Campo cpf obrigatorio")
    @Column(name="cpf", length = 15)
    private String cpf;

    @Column(name="status")
    private StatusAlunos status;

    @ManyToMany
    @JoinTable(
            name = "aluno_turma",
            joinColumns = @JoinColumn(name = "aluno_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "turma_id", referencedColumnName = "id")
    )
    private List<Turma> turmas;

}
