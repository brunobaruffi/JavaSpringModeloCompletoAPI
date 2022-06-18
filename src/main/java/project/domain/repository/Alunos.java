package project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.domain.entity.Aluno;

public interface Alunos extends JpaRepository<Aluno, Integer> {


}
