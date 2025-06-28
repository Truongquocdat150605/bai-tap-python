package com.example.truongquocdat_2123110209;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private int imageResId;
    private String price;
    private String description;
    private String gender;
    private String imageUrl;

    public Product(String name, int imageResId, String price, String description, String gender, String imageUrl) {
        this.name = name;
        this.imageResId = imageResId;
        this.price = price;
        this.description = description;
        this.gender = gender;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() { return imageUrl; }
    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
    public String getPrice() { return price; }
    public String getDescription() { return description; }
    public String getGender() { return gender; }

    // Thêm phương thức getCategory dựa trên tên
    public String getCategory() {
        String lowerName = name.toLowerCase().trim();
        if (lowerName.startsWith("áo ")) return "áo";
        if (lowerName.startsWith("quần ")) return "quần";
        if (lowerName.startsWith("váy")) return "váy";
        if (lowerName.startsWith("giày ")) return "giày";
        return "tất cả";
    }
}