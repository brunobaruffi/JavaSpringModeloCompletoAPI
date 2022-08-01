package project.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.enums.StatusAlunos;

@Entity
@Table(name="aluno")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
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
