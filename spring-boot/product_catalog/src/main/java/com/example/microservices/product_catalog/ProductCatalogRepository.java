package com.example.microservices.product_catalog;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductCatalogRepository extends MongoRepository<Product,String> {

}
