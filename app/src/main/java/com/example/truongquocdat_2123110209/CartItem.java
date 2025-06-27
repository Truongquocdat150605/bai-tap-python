package com.example.truongquocdat_2123110209;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Tính tổng giá trị của mặt hàng này
    public double getTotalPrice() {
        try {
            // Chuyển đổi giá từ String sang double để tính toán
            double price = Double.parseDouble(product.getPrice());
            return price * quantity;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0; // Trả về 0 nếu giá không hợp lệ
        }
    }
}