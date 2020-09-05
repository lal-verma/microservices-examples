package com.example.microservices.shoppingcartservicebasic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ShoppingCartService_SpringDC {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    ShoppingCartDao shoppingCartDao;

    @PostMapping("/springdc/cart/{cartId}/item")
    public Cart addItem(@PathVariable String cartId, @RequestBody CartItem item) {
        if (cartId != null && item != null && item.getProductId() != null) {
            //get instance home url
            List<ServiceInstance> instances = discoveryClient.getInstances("product_catalog");
            System.out.println("discovery order"+discoveryClient.getOrder());
            System.out.println("discovery services"+discoveryClient.getServices());
            if (instances!=null && instances.size()>0){
                ServiceInstance ins = instances.get(0);
                String productCatalogUrl = "http://"+ins.getHost()+":"+ins.getPort()+ins.getUri().getPath()+"/product/"+ item.getProductId();
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