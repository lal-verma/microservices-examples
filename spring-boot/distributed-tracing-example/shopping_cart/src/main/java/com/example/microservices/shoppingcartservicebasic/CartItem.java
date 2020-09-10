package com.example.microservices.shoppingcartservicebasic;

public class CartItem {
    String productId;
    int quantity;
    double totalItemPrice;


    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(double totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "ShoppingCartItem [productId=" + productId + ", quantity=" + quantity + ", totalItemPrice="
                + totalItemPrice + "]";
    }


}
