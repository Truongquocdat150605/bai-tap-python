package com.example.truongquocdat_2123110209;

import java.io.Serializable;

public class CheckoutProduct implements Serializable {
    private String name;
    private double price;
    private int quantity;
    private String imageUrl;

    public CheckoutProduct(String name, double price, int quantity, String imageUrl) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
