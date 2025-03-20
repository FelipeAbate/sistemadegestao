package com.abatesystem.sistemadegestao.repositories;

import com.abatesystem.sistemadegestao.dtos.PedidosRecordDetalhadoDto;
import com.abatesystem.sistemadegestao.models.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.UUID;

public interface PedidosRepository extends JpaRepository<Pedidos, UUID>{

    @Query("SELECT new com.abatesystem.sistemadegestao.dtos.PedidosRecordDetalhadoDto( " +
            "p.idPedido, p.dataCriacao, c.nomeCliente, cal.descricao, p.quant, cal.preco, p.valorTotal) " +
            "FROM Pedidos p " +
            "JOIN p.idProduto cal " +
            "JOIN p.idCliente c "
            )
    List<PedidosRecordDetalhadoDto> buscarPedidoDetalhado();
}

 /*
     O JOIN JPQL (JPA) -> PedidoDetalhadoDTO

     EM SQL PURO

     SELECT tb_pedidos.id_pedido, tb_pedidos.data_criacao, tb_clientes.nome_cliente,
	 tb_calcas.descricao, tb_pedidos.quant, tb_calcas.preco

     FROM tb_calcas

     INNER JOIN tb_pedidos ON tb_calcas.id_produto = tb_pedidos.id_produto
     INNER JOIN tb_clientes ON tb_clientes.id_cliente = tb_pedidos.id_cliente
 */




