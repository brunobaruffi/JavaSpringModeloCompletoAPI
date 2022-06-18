package project.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.enums.StatusAlunos;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="turma")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Turma {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @NotEmpty(message = "Campo nome obrigatorio")
    @Column(name="nomeTurma", length = 120)
    private String nomeTurma;

    @NotNull(message = "Campo nome obrigatorio")
    @Column(name="ano")
    private Integer ano;

    @Column(name="status")
    private StatusAlunos status;
}
