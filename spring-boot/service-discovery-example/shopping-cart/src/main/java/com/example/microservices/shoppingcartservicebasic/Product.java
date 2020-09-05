package com.example.microservices.shoppingcartservicebasic;

public class Product {

    String id;
    double unitPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", unitPrice=" + unitPrice + "]";
    }

    

}
