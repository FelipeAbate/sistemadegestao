package com.abatesystem.sistemadegestao.controllers;

import com.abatesystem.sistemadegestao.dtos.CalcasRecordDto;
import com.abatesystem.sistemadegestao.models.Calcas;
import com.abatesystem.sistemadegestao.repositories.CalcasRepository;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class ControllerEstoqueApi {

    @Autowired
    CalcasRepository calcasRepository;

    @PostMapping("/products")
    public ResponseEntity<Calcas> saveCalca(@RequestBody @Valid CalcasRecordDto calcasRecordDto) {
        var calcas = new Calcas();
        BeanUtils.copyProperties(calcasRecordDto, calcas);
        return ResponseEntity.status(HttpStatus.CREATED).body(calcasRepository.save(calcas));
    }

    @GetMapping("/products")
    public ResponseEntity<List<Calcas>> getAllCalcas(){
        List<Calcas> calcasList = calcasRepository.findAll();
        if(!calcasList.isEmpty()) {
            for(Calcas calcas : calcasList) {
                calcas.add(linkTo(methodOn(ControllerEstoqueApi.class).getOneCalcas(calcas.getIdProduto())).withSelfRel());
            }
        }
        return  ResponseEntity.status(HttpStatus.OK).body(calcasList);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneCalcas (@PathVariable(value="id") UUID id){
        Optional<Calcas> calca = calcasRepository.findById(id);

        if (calca.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        calca.get().add(linkTo(methodOn(ControllerEstoqueApi.class).getAllCalcas()).withRel("Calças List"));
        return ResponseEntity.status(HttpStatus.OK).body(calca.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateCalca(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid CalcasRecordDto calcasRecordDto) {
        Optional<Calcas> calca = calcasRepository.findById(id);
        if(calca.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        var updateCalca = calca.get();
        BeanUtils.copyProperties(calcasRecordDto, updateCalca);
        return ResponseEntity.status(HttpStatus.OK).body(calcasRepository.save(updateCalca));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteCalca(@PathVariable(value="id") UUID id) {
        Optional<Calcas> calca = calcasRepository.findById(id);
        if(calca.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        calcasRepository.delete(calca.get());
        return ResponseEntity.status(HttpStatus.OK).body("Calça deleted sucessfully");
    }

}
