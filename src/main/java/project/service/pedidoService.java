package project.service;

import project.domain.entity.Pedido;
import project.rest.dto.PedidoDTO;

public interface pedidoService {

    Pedido salvar(PedidoDTO dto);

    Pedido oberPedido(Integer Id);
}
