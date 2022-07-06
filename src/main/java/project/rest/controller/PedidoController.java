package project.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import project.domain.entity.Pedido;
import project.rest.dto.PedidoDTO;
import project.service.pedidoService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/pedido")
public class PedidoController {

    private pedidoService service;

    public PedidoController(pedidoService service) {this.service = service; }

    @Operation(
        method = "POST",
        description = "Salva novo pedido",
        summary = "Salva novo pedido",
        tags = "/api/pedido")
    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @Operation(
        method = "GET",
        description = "Obtem pedido especifico",
        summary = "Obtem pedido especifico",
        tags = "/api/pedido")
    @GetMapping(value = "{id}")
    public Pedido obterPedido(@PathVariable("id") Integer id){
        Pedido pedido = service.oberPedido(id);
        return pedido;
    }
}
