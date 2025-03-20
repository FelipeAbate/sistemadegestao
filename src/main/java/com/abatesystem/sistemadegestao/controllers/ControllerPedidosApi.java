package com.abatesystem.sistemadegestao.controllers;

import com.abatesystem.sistemadegestao.dtos.PedidosRecordDetalhadoDto;
import com.abatesystem.sistemadegestao.dtos.PedidosRecordDto;
import com.abatesystem.sistemadegestao.dtos.PedidosRecordResponseDto;
import com.abatesystem.sistemadegestao.models.Clientes;
import com.abatesystem.sistemadegestao.models.Pedidos;
import com.abatesystem.sistemadegestao.models.Calcas;
import com.abatesystem.sistemadegestao.repositories.ClientesRepository;
import com.abatesystem.sistemadegestao.repositories.PedidosRepository;

import com.abatesystem.sistemadegestao.repositories.CalcasRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class ControllerPedidosApi {

    @Autowired
    PedidosRepository pedidosRepository;

    @Autowired
    ClientesRepository clientesRepository;

    @Autowired
    CalcasRepository calcasRepository;

    @PostMapping("/order")
    public ResponseEntity<Pedidos> savePedido(@RequestBody @Valid PedidosRecordDto pedidosRecordDto) {
        var pedidos = new Pedidos();
        BeanUtils.copyProperties(pedidosRecordDto, pedidos);

        // Buscar o produto pelo ID e associá-lo ao pedido
        Calcas produto = calcasRepository.findById(pedidosRecordDto.idProduto())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        // Buscar o cliente pelo ID e associá-lo ao pedido
        Clientes cliente = clientesRepository.findById(pedidosRecordDto.idCliente())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        pedidos.setIdProduto(produto);  // Associa o produto
        pedidos.setIdCliente(cliente);  // Associa o cliente

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidosRepository.save(pedidos));
    }


    // Fazendo a requisição GET para o endpoint ('/order') Mostra todos pedidos
    // select * from tb_pedidos
    @GetMapping("/order")
    public ResponseEntity<List<PedidosRecordResponseDto>> getAllPedidos() {
        List<PedidosRecordResponseDto> pedidos = pedidosRepository.findAll()
                .stream()
                .map(PedidosRecordResponseDto::new) // Converte os pedidos para o DTO de saída
                .toList();
        return ResponseEntity.ok(pedidos);
    }

    // executa o JOIN pedidos, (tb's: calcas,pedidos,clientes)
    // exibe colunas tb_calcas + o link para todos produtos:
    // "Product List": "href": "http://localhost:8080/order"
    @GetMapping("/order/{id}")
    public ResponseEntity<Object> getOnePedido (@PathVariable(value="id") UUID id){
        Optional<Pedidos> pedido = pedidosRepository.findById(id);

        if (pedido.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        pedido.get().add(linkTo(methodOn(ControllerPedidosApi.class).getAllPedidos()).withRel("Product List"));
        return ResponseEntity.status(HttpStatus.OK).body(pedido.get());
    }

    //Faz o JOIN Pedido perfeitamente e nada mais
    @GetMapping("/order/details")
    public ResponseEntity<List<PedidosRecordDetalhadoDto>> getAllPedidosDetalhados() {
        List<PedidosRecordDetalhadoDto> pedidos = pedidosRepository.buscarPedidoDetalhado();

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(pedidos);
    }


    @PutMapping("/order/{id}")
    public ResponseEntity<Object> updatePedido(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid PedidosRecordDto pedidosRecordDto) {
        Optional<Pedidos> pedido= pedidosRepository.findById(id);
        if(pedido.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        var pedidos = pedido.get();
        BeanUtils.copyProperties(pedidosRecordDto, pedidos);
        return ResponseEntity.status(HttpStatus.OK).body(pedidosRepository.save(pedidos));
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<Object> deletePedido(@PathVariable(value="id") UUID id) {
        Optional<Pedidos> pedido = pedidosRepository.findById(id);
        if(pedido.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        pedidosRepository.delete(pedido.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted sucessfully");
    }

}
