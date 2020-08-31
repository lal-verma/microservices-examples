package com.example.microservices.product_catalog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductCatalogService {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/product")
    public Product addProduct(@RequestBody Product product){
        return mongoTemplate.insert(product);
    }

    @PutMapping("/product")
    public Product updateProduct(@RequestBody Product product){
        return mongoTemplate.save(product);        
    }

    @GetMapping("/product/{id}")
    public Product getProductDetails(@PathVariable  String id){
        return mongoTemplate.findById(id,Product.class);
    }


    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable String id) {
        Product toDeleteProduct = new Product();
        toDeleteProduct.setId(id);

        mongoTemplate.remove(toDeleteProduct);
        return "Product Deleted-"+id;
    }

    @GetMapping("/product")
    public List<Product> getProductList(){
        return mongoTemplate.findAll(Product.class);
    }

}