package com.abatesystem.sistemadegestao.repositories;

import com.abatesystem.sistemadegestao.models.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrdersRepository extends JpaRepository<Pedidos, UUID>{
}
