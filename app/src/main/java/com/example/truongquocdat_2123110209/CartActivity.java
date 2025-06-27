package com.example.truongquocdat_2123110209;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.content.Intent;

import java.text.DecimalFormat;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartItemChangeListener {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private TextView tvTotalPrice;
    private Button btnClearCart, btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnClearCart = findViewById(R.id.btnClearCart);
        btnCheckout = findViewById(R.id.btnCheckout);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<CartItem> currentCartItems = CartManager.getInstance().getCartItems();
        cartAdapter = new CartAdapter(currentCartItems, this);
        cartRecyclerView.setAdapter(cartAdapter);

        updateTotalPrice();

        btnClearCart.setOnClickListener(v -> {
            CartManager.getInstance().clearCart();
            cartAdapter.updateCartItems(CartManager.getInstance().getCartItems());
            updateTotalPrice();
            Toast.makeText(this, "Giỏ hàng đã được xóa!", Toast.LENGTH_SHORT).show();
        });

        btnCheckout.setOnClickListener(v -> {
            if (CartManager.getInstance().getTotalItemCount() > 0) {
                List<CartItem> cartItems = CartManager.getInstance().getCartItems();

                ArrayList<CheckoutProduct> checkoutList = new ArrayList<>();
                for (CartItem item : cartItems) {
                    Product product = item.getProduct(); // 👉 Lấy đối tượng Product
                    checkoutList.add(new CheckoutProduct(
                            product.getName(),
                            Double.parseDouble(product.getPrice()),
                            item.getQuantity(),
                            product.getImageUrl()
                    ));
                }

                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                intent.putExtra("checkout_list", checkoutList);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Giỏ hàng của bạn đang trống!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTotalPrice() {
        // Sử dụng getTotalPrice() thay vì getTotalCartPrice()
        double total = CartManager.getInstance().getTotalPrice();
        DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");
        tvTotalPrice.setText("Tổng cộng: " + formatter.format(total));
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartAdapter.updateCartItems(CartManager.getInstance().getCartItems());
        updateTotalPrice();
    }

    @Override
    public void onQuantityChanged() {
        updateTotalPrice();
    }

    @Override
    public void onItemRemoved() {
        updateTotalPrice();
        cartAdapter.updateCartItems(CartManager.getInstance().getCartItems());
    }
}