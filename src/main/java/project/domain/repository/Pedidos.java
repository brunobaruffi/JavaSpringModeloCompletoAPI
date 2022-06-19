package project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.domain.entity.Pedido;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

}
