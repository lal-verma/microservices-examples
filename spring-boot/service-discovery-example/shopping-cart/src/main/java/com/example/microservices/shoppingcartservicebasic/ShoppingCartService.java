package com.example.microservices.shoppingcartservicebasic;

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

    @PostMapping("/cart/{cartId}/item")
    public Cart addItem(@PathVariable String cartId, @RequestBody CartItem item) {
        if (cartId != null && item != null && item.getProductId() != null) {
            // get product details
            Product itemProduct = restTemplate.getForObject("http://localhost:8080/product/" + item.getProductId(),
                    Product.class);

            if (itemProduct != null && itemProduct.id != null) {
                // adding total item price in the shopping cart item
                item.setTotalItemPrice(itemProduct.getUnitPrice() * item.quantity);
                return shoppingCartDao.addItem(cartId, item);
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item product not found");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cart or item missing");
    }

}