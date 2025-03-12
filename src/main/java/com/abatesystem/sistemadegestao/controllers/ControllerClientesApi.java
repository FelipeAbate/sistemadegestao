package com.abatesystem.sistemadegestao.controllers;


import com.abatesystem.sistemadegestao.dtos.ClientesRecordDto;
import com.abatesystem.sistemadegestao.models.Clientes;
import com.abatesystem.sistemadegestao.repositories.ClientesRepository;
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
public class ControllerClientesApi {

    @Autowired
    ClientesRepository clientesRepository;

    @PostMapping("/customers")
    public ResponseEntity<Clientes> saveCliente(@RequestBody @Valid ClientesRecordDto clientesRecordDto) {
        var cliente = new Clientes();
        BeanUtils.copyProperties(clientesRecordDto, cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientesRepository.save(cliente));
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Clientes>> getAllClientes(){
        List<Clientes> clientesList = clientesRepository.findAll();
        if(!clientesList.isEmpty()) {
            for(Clientes cliente : clientesList) {
                cliente.add(linkTo(methodOn(ControllerClientesApi.class).getOneCliente(cliente.getIdCliente())).withSelfRel());
            }
        }
        return  ResponseEntity.status(HttpStatus.OK).body(clientesList);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Object> getOneCliente(@PathVariable(value="id") UUID id){
        Optional<Clientes> cliente1 = clientesRepository.findById(id);

        if (cliente1.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("client not found");
        }
        cliente1.get().add(linkTo(methodOn(ControllerClientesApi.class).getAllClientes()).withRel("Clientes List"));
        return ResponseEntity.status(HttpStatus.OK).body(cliente1.get());
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Object> updateCliente(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid ClientesRecordDto clientesRecordDto) {
        Optional<Clientes> cliente = clientesRepository.findById(id);
        if(cliente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        var clientes = cliente.get();
        BeanUtils.copyProperties(clientesRecordDto, clientes);
        return ResponseEntity.status(HttpStatus.OK).body(clientesRepository.save(clientes));
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Object> deleteCliente(@PathVariable(value="id") UUID id) {
        Optional<Clientes> cliente= clientesRepository.findById(id);
        if(cliente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente not found");
        }
        clientesRepository.delete(cliente.get());
        return ResponseEntity.status(HttpStatus.OK).body("Cliente deleted sucessfully");
    }

}
