package com.assessment.todoProductApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.assessment.todoProductApp.dto.ProductDto;
import com.assessment.todoProductApp.dto.ProductResponse;
import com.assessment.todoProductApp.exceptions.ProductNotFoundException;
import com.assessment.todoProductApp.models.Product;
import com.assessment.todoProductApp.repo.ProductRepository;
import com.assessment.todoProductApp.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setStatus(productDto.getStatus());      
             
        Number num = productRepository.createProduct(product);           
        ProductDto productResponse = new ProductDto();
        productResponse.setId(num.longValue());    
        productResponse.setMessage("Product added successfully ");
        return productResponse;
    }

   @Override
    public List<ProductDto> getAllProduct() {
	   
        List<Product> products = productRepository.getAllProducts();
        if(CollectionUtils.isEmpty(products))
        {
        	throw new ProductNotFoundException("No products found! Please create some products");
        }
        List<ProductDto> content = products.stream().map(p -> mapToDto(p)).collect(Collectors.toList());        		

        ProductResponse productResponse = new ProductResponse();     
        productResponse.setContent(content);        
       
        return productResponse.getContent();
    } 

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.getProductById(id);        
        return mapToDto(product);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
    	Product product = productRepository.getProductById(productDto.getId());  	

        product.setName(productDto.getName());
	    product.setStatus(productDto.getStatus());	    
	    
	    if(productRepository.updateProduct(product)) {
	    	productDto = mapToDto(product);
	    	productDto.setMessage("Product updated successfully");
	      	return productDto;
	    }
	      	throw new ProductNotFoundException("Update operation failed! No product found for update");
	    }

    @Override
    public ProductDto deleteProductId(Long productId) {
    	
    	if(productRepository.deleteProduct(productId)) {
        	ProductDto productResponse = new ProductDto();
        	productResponse.setMessage("Product deleted successfully! ");
            
            return productResponse;
        	
        }
        else
        	throw new ProductNotFoundException("Operation failed! Cannot delete product");        	
    }

    private ProductDto mapToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setStatus(product.getStatus());      
        return productDto;
    }    
}