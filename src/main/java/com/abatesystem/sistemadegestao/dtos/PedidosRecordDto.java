package com.abatesystem.sistemadegestao.dtos;


import java.math.BigDecimal;
import java.util.UUID;

public record PedidosRecordDto(UUID idCliente, UUID idProduto, int quant, BigDecimal valorTotal) {
}



