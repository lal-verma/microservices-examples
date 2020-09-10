package com.example.microservices.shoppingcartservicebasic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ShoppingCartService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ShoppingCartDao shoppingCartDao;

    Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);

    @PostMapping("/cart/{cartId}/item")
    public Cart addItem(@PathVariable String cartId, @RequestBody CartItem item) {
        logger.info("add item - process started");
        if (cartId != null && item != null && item.getProductId() != null) {

            logger.info("add item - calling product catalog service");
            Product itemProduct = restTemplate.getForObject("http://localhost:8081/product/" + item.getProductId(),
                    Product.class);

            if (itemProduct != null && itemProduct.id != null) {
                // adding total item price in the shopping cart item
                item.setTotalItemPrice(itemProduct.getUnitPrice() * item.quantity);
                Cart cart = shoppingCartDao.addItem(cartId, item);
                logger.info("add item - process completed successfully");
                return cart;
            }
            logger.warn("add item - item product not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item product not found");
        }
        logger.error("add item - cart or item missing");
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cart or item missing");
    }

}