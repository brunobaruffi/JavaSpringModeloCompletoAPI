package project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.domain.entity.Aluno;
import java.util.List;

public interface Alunos extends JpaRepository<Aluno, Integer> {

    @Query(value = "SELECT a.* FROM Aluno a, Turma t, Aluno_Turma at WHERE a.ID = at.Aluno_ID AND t.ID=at.Turma_ID AND at.Turma_ID = :id",nativeQuery = true)
    List<Aluno> findAlunoByTurma(@Param("id") Integer id);
}
