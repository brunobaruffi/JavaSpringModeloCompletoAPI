package project.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TurmaAlunoDTO {

    @NotNull(message = "Campos obrigatorios faltantes")
    private Integer aluno;
    @NotNull(message = "Campos obrigatorios faltantes")
    private List<Integer> turmas;
}
