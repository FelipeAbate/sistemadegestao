package com.abatesystem.sistemadegestao.dtos;

import com.abatesystem.sistemadegestao.models.ProdutoCalcas;

import java.time.LocalDateTime;
import java.util.UUID;


public record PedidosRecordDto(LocalDateTime dataPedido, int quantidadePedida, UUID idProduto) {
}



