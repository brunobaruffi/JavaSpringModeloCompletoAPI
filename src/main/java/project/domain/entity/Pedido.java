package project.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.enums.StatusAlunos;
import project.domain.enums.StatusPedido;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="pedido")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pedido {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @ManyToOne()
    @JoinColumn(name = "alunoId")
    private Aluno alunoId;

    @NotEmpty(message = "Campo nome obrigatorio")
    @Column(name="descricao", length = 120)
    private String descricao;

    @NotNull(message = "Data Obrigatoria")
    @Column(name="dataPedido")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dataPedido;

    @Column(name="status")
    private StatusPedido status;
}
