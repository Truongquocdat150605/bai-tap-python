package com.example.truongquocdat_2123110209;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addProduct(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getName().equals(product.getName())) { // Giả định tên sản phẩm là duy nhất
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cartItems.add(new CartItem(product, quantity));
    }

    // Lấy danh sách sản phẩm trong giỏ hàng
    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems); // Trả về bản sao để tránh sửa đổi trực tiếp
    }

    // Cập nhật số lượng sản phẩm
    public void updateProductQuantity(Product product, int newQuantity) { // Tên phương thức đúng
        for (CartItem item : cartItems) {
            if (item.getProduct().getName().equals(product.getName())) {
                if (newQuantity > 0) {
                    item.setQuantity(newQuantity);
                } else {
                    cartItems.remove(item); // Xóa sản phẩm nếu số lượng là 0 hoặc âm
                }
                return;
            }
        }
    }

    // Xóa một sản phẩm khỏi giỏ hàng
    public void removeItem(Product product) { // Tên phương thức đúng
        CartItem itemToRemove = null;
        for (CartItem item : cartItems) {
            if (item.getProduct().getName().equals(product.getName())) {
                itemToRemove = item;
                break;
            }
        }
        if (itemToRemove != null) {
            cartItems.remove(itemToRemove);
        }
    }

    // Xóa toàn bộ giỏ hàng
    public void clearCart() {
        cartItems.clear();
    }

    // Tính tổng số lượng sản phẩm trong giỏ hàng (cho badge)
    public int getTotalItemCount() {
        int count = 0;
        for (CartItem item : cartItems) {
            count += item.getQuantity();
        }
        return count;
    }

    // Tính tổng giá trị của giỏ hàng
    public double getTotalPrice() { // Tên phương thức đúng
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        return total;
    }
}