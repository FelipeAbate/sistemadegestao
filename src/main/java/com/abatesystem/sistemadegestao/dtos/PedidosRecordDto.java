package com.abatesystem.sistemadegestao.dtos;


import java.util.UUID;

public record PedidosRecordDto(UUID idCliente, UUID idProduto, int quant) {
}



