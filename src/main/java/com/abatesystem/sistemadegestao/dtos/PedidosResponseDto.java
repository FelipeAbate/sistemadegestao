package com.abatesystem.sistemadegestao.dtos;

import com.abatesystem.sistemadegestao.models.Pedidos;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.UUID;

public record PedidosResponseDto(UUID idPedido, LocalDateTime dataCriacao, UUID idCliente,
                                 UUID idProduto, int quant, BigDecimal valorTotal) {
    public PedidosResponseDto(Pedidos pedido) {
        this(pedido.getIdPedido(),
                pedido.getDataCriacao(),
                pedido.getIdCliente().getIdCliente(),
                pedido.getIdProduto().getIdProduto(),
                pedido.getQuant(),
                pedido.getValorTotal());
    }
}

