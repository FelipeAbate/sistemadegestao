package com.abatesystem.sistemadegestao.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PedidoDetalhadoDTO(
        UUID idPedido,
        LocalDateTime dataCriacao,
        String nomeCliente,
        String descricao,
        int quant,
        BigDecimal preco,
        BigDecimal valorTotal
){}

//  DTO para JOIN