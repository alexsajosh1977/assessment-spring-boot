package com.assessment.todoProductApp.service;

import java.util.List;

import com.assessment.todoProductApp.dto.ProductDto;

public interface ProductService {
	
	ProductDto createProduct(ProductDto productDto);
	List<ProductDto> getAllProduct();
    ProductDto getProductById(Long id);
    ProductDto updateProduct(ProductDto productDto);
    ProductDto deleteProductId(Long id);

}
