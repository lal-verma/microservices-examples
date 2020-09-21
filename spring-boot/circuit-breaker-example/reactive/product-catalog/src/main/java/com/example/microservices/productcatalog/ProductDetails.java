package com.example.microservices.productcatalog;


public class ProductDetails extends Product{
    private ProductInventory inventory;

    public ProductInventory getInventory() {
        return inventory;
    }

    public void setInventory(ProductInventory inventory) {
        this.inventory = inventory;
    }


}
