package com.abatesystem.sistemadegestao.repositories;

import com.abatesystem.sistemadegestao.models.Calcas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CalcasRepository extends JpaRepository<Calcas, UUID> {
}
