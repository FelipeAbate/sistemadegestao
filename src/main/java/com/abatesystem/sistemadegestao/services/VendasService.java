package com.abatesystem.sistemadegestao.services;


import com.abatesystem.sistemadegestao.models.Calcas;
import com.abatesystem.sistemadegestao.repositories.CalcasRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VendasService {

    private CalcasRepository calcasRepository;

    public List<Calcas> getAllCalcas() {
        return calcasRepository.findAll();
    }

    public Optional<Calcas> getOneCalcaById(UUID id) {
        return calcasRepository.findById(id);
    }
}
