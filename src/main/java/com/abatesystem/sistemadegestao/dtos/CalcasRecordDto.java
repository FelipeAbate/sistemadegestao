package com.abatesystem.sistemadegestao.dtos;

import java.math.BigDecimal;

public record CalcasRecordDto(String descricao, BigDecimal preco, int quant, String tamanho) {
}
