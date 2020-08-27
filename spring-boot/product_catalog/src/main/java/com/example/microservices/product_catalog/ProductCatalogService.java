package com.example.microservices.product_catalog;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerResponse;

@RestController
public class ProductCatalogService {
    
    @Autowired
    private ProductCatalogRepository repository;

    @PostMapping("/product")
    public Product addProduct(@RequestBody Product product){
        return repository.insert(product);
    }

    @PutMapping("/product")
    public String updateProduct(@RequestBody Product product){
        repository.save(product);
        return "product updated successfully-"+product.getId();
    }

    @GetMapping("/product/{id}")
    public Optional<Product> getProductDetails(@PathVariable  String id){
        return repository.findById(id);
    }


    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable String id) {
        repository.deleteById(id);
        return "Product Deleted-"+id;
    }

}