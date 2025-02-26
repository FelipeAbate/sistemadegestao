package com.abatesystem.sistemadegestao.dtos;

import com.abatesystem.sistemadegestao.models.Pedidos;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record PedidosResponseDto(UUID idPedido, String dataPedido, int quantidadePedida) {
    public PedidosResponseDto(Pedidos pedido) {
        this(pedido.getIdPedido(),
                pedido.getDataPedido().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                pedido.getQuantidadePedida());
    }

}

