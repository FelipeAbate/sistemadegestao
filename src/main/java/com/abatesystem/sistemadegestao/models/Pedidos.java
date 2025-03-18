package com.abatesystem.sistemadegestao.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
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

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Clientes idCliente;

    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private Calcas idProduto;

    private int quant;

    private BigDecimal valorTotal;

    public UUID getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(UUID idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Calcas getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Calcas idProduto) {
        this.idProduto = idProduto;
    }

    public Clientes getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Clientes idCliente) {
        this.idCliente = idCliente;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValoraTotal(BigDecimal valoraTotal) {
        this.valorTotal = valoraTotal;
    }
}
