package com.abatesystem.sistemadegestao.repositories;

import com.abatesystem.sistemadegestao.models.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientesRepository extends JpaRepository<Clientes, UUID> {
}
