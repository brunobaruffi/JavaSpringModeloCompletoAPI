package project.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import project.domain.entity.Aluno;
import project.domain.entity.Pedido;
import project.domain.repository.Alunos;
import project.domain.repository.Pedidos;
import project.rest.dto.PedidoDTO;
import project.service.pedidoService;

@Service // para serviços
@RequiredArgsConstructor
public class pedidoServiceImpl implements pedidoService {

    private final Alunos alunosRepository;
    private final Pedidos pedidosRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idaluno = dto.getAluno();
        Aluno aluno = alunosRepository.findById(idaluno)
                .orElseThrow(
                        ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Codigo de aluno Invalido.")
                );
        Pedido pedido = new Pedido();
        pedido.setDescricao(dto.getDescricao());
        pedido.setDataPedido(dto.getDataPedido());
        pedido.setStatus(dto.getStatus());
        pedido.setAlunoId(aluno);
        pedidosRepository.save(pedido);
        return pedido;
    }

    @Override
    public Pedido oberPedido(Integer Id) {
        Integer idpedido = Id;
        Pedido pedido = pedidosRepository.findById(idpedido)
                .orElseThrow(
                        ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Codigo de produto Invalido.")
                );
        Integer idAluno = pedido.getAlunoId().getId();
        Aluno aluno = alunosRepository.findById(idAluno)
                .orElseThrow(
                        ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Codigo de aluno Invalido.")
                );
        Pedido pedidoSaida = new Pedido();
        pedidoSaida = pedido;
        pedidoSaida.setAlunoId(aluno);
        return pedidoSaida;
    }

}
