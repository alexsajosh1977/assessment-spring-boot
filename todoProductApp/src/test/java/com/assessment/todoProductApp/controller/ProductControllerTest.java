package com.assessment.todoProductApp.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.assessment.todoProductApp.dto.ProductDto;
import com.assessment.todoProductApp.models.Product;
import com.assessment.todoProductApp.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {


	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	@Autowired
	private ObjectMapper objectMapper;
	private Product product;
	private ProductDto productDto;
		
	@BeforeEach
	public void init() {
			product = Product.builder().name("Apple").status("available").build();
			productDto = ProductDto.builder().name("Apple").status("available").build();	       
	}
	
	@Test
	public void ProductController_CreateProduct_ReturnCreated() throws Exception {
		given(productService.createProduct(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

		ResultActions response = mockMvc.perform(post("/product/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productDto)));				

		response.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(productDto.getName())))
		.andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(productDto.getStatus())));
				
	}
	
	
	@Test
	public void ProductController_ProductDetail_ReturnProductDto() throws Exception {
		Long productId = 1L;
		when(productService.getProductById(productId)).thenReturn(productDto);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/product/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productDto)));

		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(productDto.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(productDto.getStatus())));
	}

	@Test
	public void ProductController_UpdateProduct_ReturnProductDto() throws Exception {
		productDto = ProductDto.builder().id(1L).name("Apple").status("available").build();
		when(productService.updateProduct(productDto)).thenReturn(productDto);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/product/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productDto)));

		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(productDto.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(productDto.getStatus())));
	}

	@Test
	public void ProductController_DeleteProduct_ReturnProductDto_Message() throws Exception {
		Long productId = 1L;
		productDto = ProductDto.builder().message("Deleted").build();
		when(productService.deleteProductId(productId)).thenReturn(productDto);

		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productDto)));

		response.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.message", CoreMatchers.is(productDto.getMessage())));
	}



}
