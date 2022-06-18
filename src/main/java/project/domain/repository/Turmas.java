package project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.domain.entity.Turma;

public interface Turmas  extends JpaRepository<Turma, Integer> {

}
