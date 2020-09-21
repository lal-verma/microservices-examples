package com.example.microservices.productcatalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProductCatalogService {

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Autowired
    private WebClient webClient;

    @Autowired
    private ReactiveCircuitBreakerFactory cbFactory;

    @GetMapping("/product/{id}")
    public Mono<ProductDetails> getProductDetailsV2(@PathVariable String id) {
        Mono<ProductDetails> productDetailsMono = mongoTemplate.findById(id, ProductDetails.class);

        // circuitbreaker
        Mono<ProductInventory> inventoryMono = webClient.get().uri("http://localhost:8082/inventory/" + id).retrieve()
                .bodyToMono(ProductInventory.class)
                .transform(it -> cbFactory.create("inventory-service").run(it, throwable -> {
                    return Mono.just(new ProductInventory());
                }));

        Mono<ProductDetails> mergedProductDetails = Mono.zip(productDetailsMono, inventoryMono,
                (productDetails, inventory) -> {
                    productDetails.setInventory(inventory);
                    return productDetails;
                });

        return mergedProductDetails;
    }

    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable String id) {
        Product toDeleteProduct = new Product();
        toDeleteProduct.setId(id);

        mongoTemplate.remove(toDeleteProduct);
        return "Product Deleted-" + id;
    }

    @GetMapping("/product")
    public Flux<Product> getProductList() {
        return mongoTemplate.findAll(Product.class);
    }

    @PostMapping("/product")
    public Mono<Product> addProduct(@RequestBody Product product) {
        return mongoTemplate.insert(product);
    }

    @PutMapping("/product")
    public Mono<Product> updateProduct(@RequestBody Product product) {
        return mongoTemplate.save(product);
    }

}