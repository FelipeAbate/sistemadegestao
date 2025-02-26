package com.abatesystem.sistemadegestao.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_PEDIDOS")
public class Pedidos extends RepresentationModel<Pedidos> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idPedido;

    private LocalDateTime dataPedido;
    private int quantidadePedida;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private ProdutoCalcas idProduto;

    public UUID getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(UUID idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }


    public int getQuantidadePedida() {
        return quantidadePedida;
    }

    public void setQuantidadePedida(int quantidadePedida) {
        this.quantidadePedida = quantidadePedida;
    }

    public ProdutoCalcas getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(ProdutoCalcas idProduto) {
        this.idProduto = idProduto;
    }
}
