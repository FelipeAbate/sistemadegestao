package com.abatesystem.sistemadegestao.dtos;

import com.abatesystem.sistemadegestao.models.Pedidos;


import java.math.BigDecimal;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record PedidosRecordResponseDto(
        UUID idPedido,
        String dataCriacao,
        UUID idCliente,
        UUID idProduto,
        int quant,
        BigDecimal valorTotal) {

    public PedidosRecordResponseDto(Pedidos pedido) {
        this(
                pedido.getIdPedido(),
                pedido.getDataCriacao().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), // Formata para o HTML
                pedido.getIdCliente().getIdCliente(),
                pedido.getIdProduto().getIdProduto(),
                pedido.getQuant(),
                pedido.getValorTotal()
        );
    }
}



