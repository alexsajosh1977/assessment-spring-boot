package com.assessment.todoProductApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.todoProductApp.dto.ProductDto;
import com.assessment.todoProductApp.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class ProductController {
	
	private ProductService productService;	

    @Autowired
    public ProductController(ProductService productService) {    	
        this.productService = productService;
    }
    
    
    @GetMapping("product")
    public ResponseEntity<List> getAllProducts( ) {
        return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
    } 

    @GetMapping("product/{id}")
    public ResponseEntity<ProductDto> ProductDetail( @PathVariable Long id) {
    	log.info("Getting product with id {}", id);
        return ResponseEntity.ok(productService.getProductById(id));

    }
        
    @PostMapping("product/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
    	log.info("Creating product");
        return new ResponseEntity<>( productService.createProduct(productDto), HttpStatus.CREATED);        
    }
    
    @PutMapping("product/update")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody @Validated ProductDto productDto) {
    	log.info("Updating product");
        ProductDto response = productService.updateProduct(productDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("product/delete/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long productId) {        
    	log.info("Deleting product with id {}", productId);
    	return new ResponseEntity<>(productService.deleteProductId(productId), HttpStatus.OK);
    }


}
