package project.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.enums.StatusPedido;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    @NotNull(message = "Campo aluno obrigatorio")
    private Integer aluno;
    @NotNull(message = "Descricao obrigatoria")
    private String descricao;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dataPedido;
    @NotNull(message = "Status obrigatorio")
    private StatusPedido status;

}
