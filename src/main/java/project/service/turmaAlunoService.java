package project.service;

import project.domain.entity.Aluno;
import project.rest.dto.*;

import java.util.List;

public interface turmaAlunoService {

    Aluno salvarTurmaAluno (TurmaAlunoDTO turmaAluno);

    Aluno deletarTurmaAluno (TurmaAlunoDTO turmaAluno);

    List<Aluno> listarAlunosPorTurma(Integer id);

}
