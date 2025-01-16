package com.abatesystem.sistemadegestao.repositories;

import com.abatesystem.sistemadegestao.models.ProdutoCalcas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProdutoCalcas, UUID> {
}
