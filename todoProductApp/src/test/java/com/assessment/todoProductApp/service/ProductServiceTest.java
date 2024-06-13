package com.assessment.todoProductApp.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.assessment.todoProductApp.dto.ProductDto;
import com.assessment.todoProductApp.models.Product;
import com.assessment.todoProductApp.repo.ProductRepository;
import com.assessment.todoProductApp.service.impl.ProductServiceImpl;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
	 @Mock
	 private ProductRepository productRepository;

	 @InjectMocks
	 private ProductServiceImpl productService;
	 
	 @Test
	public void ProductService_CreateProduct_ReturnsProduct() {		
			
		ProductDto productDto = ProductDto.builder().name("Apple").status("Availabe").build();

		Number productId = 1;
		when(productRepository.createProduct(Mockito.any(Product.class))).thenReturn(productId);

		ProductDto newProdDto = productService.createProduct(productDto);

		Assertions.assertThat(newProdDto).isNotNull();
		Assertions.assertThat(newProdDto.getId()).isGreaterThan(0);
	}
	 
	@Test
	public void ProductService_GetAllProduct_ReturnsResponseDto() {
	
		Product product = Product.builder().name("Apple").status("Availabe").build();

		List<Product> productList = new ArrayList<>();
		productList.add(product);
		when(productRepository.getAllProducts()).thenReturn(productList);

		List<ProductDto> allProducts = productService.getAllProduct();

		Assertions.assertThat(allProducts).isNotNull();
		Assertions.assertThat((allProducts).size()).isGreaterThan(0);
	}

	@Test
    public void ProductService_getProductById_ReturnProductDto() {

        Long productId = 1L;        
        Product product = Product.builder().name("Apple").status("Availabe").build();
        
        when(productRepository.getProductById(productId)).thenReturn(product);

        ProductDto productReturn = productService.getProductById(productId);

        Assertions.assertThat(productReturn).isNotNull();
    }

	@Test
    public void ProductService_UpdateProduct_ReturnProductDto() {
        Long productId = 1L;
        boolean flag = true;
        
        Product product = Product.builder().name("Apple").status("Availabe").build();        
        ProductDto productDto = ProductDto.builder().id(productId).name("Apple").status("Availabe").build();       

        when(productRepository.getProductById(productId)).thenReturn(product);
        when(productRepository.updateProduct(product)).thenReturn(flag);

        ProductDto updateReturn = productService.updateProduct(productDto);

        Assertions.assertThat(updateReturn).isNotNull();
    }

	@Test
    public void ProductService_DeleteProductById_ReturnVoid() {
        Long productId = 1L;
        boolean flag = true;
        
        when(productRepository.deleteProduct(productId)).thenReturn(flag);        
        
        ProductDto newProdDto = productService.deleteProductId(productId);

        Assertions.assertThat(newProdDto).isNotNull();
    }


}
