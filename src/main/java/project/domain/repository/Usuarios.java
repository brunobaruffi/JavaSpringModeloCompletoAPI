package project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.domain.entity.Usuario;

import java.util.Optional;

public interface Usuarios extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);
}
