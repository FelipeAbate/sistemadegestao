package com.abatesystem.sistemadegestao.controllers;

import com.abatesystem.sistemadegestao.dtos.CalcasRecordDto;
import com.abatesystem.sistemadegestao.models.ProdutoCalcas;
import com.abatesystem.sistemadegestao.repositories.ProductRepository;

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
public class StockController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<ProdutoCalcas> saveProduct(@RequestBody @Valid CalcasRecordDto calcasRecordDto) {
        var produtoCalcas = new ProdutoCalcas();
        BeanUtils.copyProperties(calcasRecordDto, produtoCalcas);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(produtoCalcas));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProdutoCalcas>> getAllProducts(){
        List<ProdutoCalcas> productsList = productRepository.findAll();
        if(!productsList.isEmpty()) {
            for(ProdutoCalcas product : productsList) {
                product.add(linkTo(methodOn(StockController.class).getOneProduct(product.getIdProduto())).withSelfRel());
            }
        }
        return  ResponseEntity.status(HttpStatus.OK).body(productsList);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct (@PathVariable(value="id") UUID id){
        Optional<ProdutoCalcas> productO = productRepository.findById(id);

        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        productO.get().add(linkTo(methodOn(StockController.class).getAllProducts()).withRel("Product List"));
        return ResponseEntity.status(HttpStatus.OK).body(productO.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid CalcasRecordDto calcasRecordDto) {
        Optional<ProdutoCalcas> productO = productRepository.findById(id);
        if(productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        var produtoCalcas = productO.get();
        BeanUtils.copyProperties(calcasRecordDto, produtoCalcas);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(produtoCalcas));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value="id") UUID id) {
        Optional<ProdutoCalcas> productO = productRepository.findById(id);
        if(productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productRepository.delete(productO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted sucessfully");
    }

}
