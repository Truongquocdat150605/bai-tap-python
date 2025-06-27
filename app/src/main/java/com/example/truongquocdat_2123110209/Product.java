package com.example.truongquocdat_2123110209;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private int imageResId;
    private String price;
    private String description;
    private String gender;
    private String imageUrl; // ✅ Thêm biến này

    public Product(String name, int imageResId, String price, String description, String gender, String imageUrl) {
        this.name = name;
        this.imageResId = imageResId;
        this.price = price;
        this.description = description;
        this.gender = gender;
        this.imageUrl = imageUrl; // ✅ Gán giá trị
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getGender() {
        return gender;
    }
}
