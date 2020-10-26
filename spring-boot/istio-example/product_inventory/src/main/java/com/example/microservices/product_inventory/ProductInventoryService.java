package com.example.microservices.product_inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductInventoryService {
    
    @Autowired
    private MongoTemplate mongoTemplate;


    @PostMapping("/inventory")
    public ProductInventory addProductInventory(@RequestBody ProductInventory product){
        return mongoTemplate.insert(product);
    }

    @PutMapping("/inventory")
    public ProductInventory updateProductInventory(@RequestBody ProductInventory product){
        return mongoTemplate.save(product);        
    }

    @GetMapping("/inventory/{id}")
    public ProductInventory getProductInventory(@PathVariable  String id){
        return mongoTemplate.findById(id,ProductInventory.class);
    }

}