package com.example.microservices.product_catalog;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductCatalogApplicationTests {

	@Autowired
	private MockMvc mvc;

	private String testProductId="test-product-444";

	@Test
	@Order(1)
	public void testCreateProduct() throws Exception{
		this.mvc.perform(post("/product")
           .contentType(MediaType.APPLICATION_JSON)
           .content("{\"id\":\""+testProductId+"\",\"title\":\"test-product-2\",\"desc\":\"test product 2\",\"imagePath\":\"gc://image-path\",\"unitPrice\":10.00}") 
           .accept(MediaType.APPLICATION_JSON))
		   .andExpect(status().isOk());
	}

	@Test
	@Order(2)
	public void testGetProductDetails() throws Exception {
		this.mvc.perform(get("/product/"+testProductId))
		.andDo(print())
		.andExpect(status().isOk());
	}

	@Test
	@Order(3)
	public void testUpdateProduct() throws Exception{
		this.mvc.perform(put("/product")
           .contentType(MediaType.APPLICATION_JSON)
           .content("{\"id\":\""+testProductId+"\",\"title\":\"test-product-updated\",\"desc\":\"test product updated\",\"imagePath\":\"gc://image-path\",\"unitPrice\":10.00}")) 
		   .andExpect(status().isOk());
	}



	@Test
	@Order(4)
	public void testGetProductDetails_v2() throws Exception {
		this.mvc.perform(get("/product/"+testProductId))
		.andDo(print())
		.andExpect(status().isOk());
	}


	@Test
	@Order(5)
	public void testDeleteProduct() throws Exception {
		this.mvc.perform(delete("/product/"+testProductId))
		.andExpect(status().isOk());
	}


}
