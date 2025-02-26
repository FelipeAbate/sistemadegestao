package com.abatesystem.sistemadegestao.controllers;

import com.abatesystem.sistemadegestao.dtos.PedidosRecordDto;
import com.abatesystem.sistemadegestao.dtos.PedidosResponseDto;
import com.abatesystem.sistemadegestao.models.Pedidos;
import com.abatesystem.sistemadegestao.models.ProdutoCalcas;
import com.abatesystem.sistemadegestao.repositories.OrdersRepository;

import com.abatesystem.sistemadegestao.repositories.ProductRepository;
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
public class OrdersController {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/order")
    public ResponseEntity<Pedidos> savePedido(@RequestBody @Valid PedidosRecordDto pedidosRecordDto) {
        var pedidos = new Pedidos();
        BeanUtils.copyProperties(pedidosRecordDto, pedidos);

        // Buscar o produto pelo ID e associá-lo ao pedido
        ProdutoCalcas produto = productRepository.findById(pedidosRecordDto.idProduto())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        pedidos.setIdProduto(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ordersRepository.save(pedidos));
    }

    @GetMapping("/order")
    public ResponseEntity<List<PedidosResponseDto>> getAllPedidos() {
        List<PedidosResponseDto> pedidos = ordersRepository.findAll()
                .stream()
                .map(PedidosResponseDto::new) // Converte os pedidos para o DTO de saída
                .toList();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Object> getOnePedido (@PathVariable(value="id") UUID id){
        Optional<Pedidos> pedido = ordersRepository.findById(id);

        if (pedido.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        pedido.get().add(linkTo(methodOn(OrdersController.class).getAllPedidos()).withRel("Product List"));
        return ResponseEntity.status(HttpStatus.OK).body(pedido.get());
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<Object> updatePedido(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid PedidosRecordDto pedidosRecordDto) {
        Optional<Pedidos> pedido= ordersRepository.findById(id);
        if(pedido.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        var pedidos = pedido.get();
        BeanUtils.copyProperties(pedidosRecordDto, pedidos);
        return ResponseEntity.status(HttpStatus.OK).body(ordersRepository.save(pedidos));
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<Object> deletePedido(@PathVariable(value="id") UUID id) {
        Optional<Pedidos> pedido = ordersRepository.findById(id);
        if(pedido.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        ordersRepository.delete(pedido.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted sucessfully");
    }

}
