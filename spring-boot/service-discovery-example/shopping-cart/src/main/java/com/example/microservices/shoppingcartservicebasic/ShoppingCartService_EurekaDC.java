package com.example.microservices.shoppingcartservicebasic;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ShoppingCartService_EurekaDC {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EurekaClient discoveryClient;

    @Autowired
    ShoppingCartDao shoppingCartDao;

    @PostMapping("/eurekadc/cart/{cartId}/item")
    public Cart addItem(@PathVariable String cartId, @RequestBody CartItem item) {
        if (cartId != null && item != null && item.getProductId() != null) {
            //get instance home url
            InstanceInfo instance = discoveryClient.getNextServerFromEureka("product_catalog",false);
            System.out.println("discovery order"+instance.getHomePageUrl());
            if (instance!=null){
                String productCatalogUrl = instance.getHomePageUrl()+"/product/"+ item.getProductId();
                System.out.println("productCatalogUrl - "+ productCatalogUrl);
                // get product details
                Product itemProduct = restTemplate.getForObject(productCatalogUrl,Product.class);

                System.out.println("itemProduct:" + itemProduct);

                if (itemProduct != null && itemProduct.id != null) {
                    // adding total item price in the shopping cart item
                    item.setTotalItemPrice(itemProduct.getUnitPrice() * item.quantity);
                    return shoppingCartDao.addItem(cartId, item);
                }
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item product not found");
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product catalog instances not found");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cart or item missing");
    }

}