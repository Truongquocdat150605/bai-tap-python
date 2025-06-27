package com.example.truongquocdat_2123110209;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView rvCheckoutProducts;
    private CheckoutProductAdapter adapter;
    private List<CheckoutProduct> productList;
    private TextView tvGrandTotal;
    private Button btnPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Ánh xạ View
        rvCheckoutProducts = findViewById(R.id.rvCheckoutProducts);
        tvGrandTotal = findViewById(R.id.tvGrandTotal);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        productList = (ArrayList<CheckoutProduct>) intent.getSerializableExtra("checkout_list");
        if (productList == null) productList = new ArrayList<>();

        // Setup RecyclerView
        adapter = new CheckoutProductAdapter(this, productList);
        rvCheckoutProducts.setLayoutManager(new LinearLayoutManager(this));
        rvCheckoutProducts.setAdapter(adapter);

        // Cập nhật tổng tiền
        updateTotalPrice();

        btnPlaceOrder.setOnClickListener(v -> {
            Toast.makeText(this, "Đặt hàng thành công (giả lập)!", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateTotalPrice() {
        double total = 0;
        for (CheckoutProduct product : productList) {
            total += product.getPrice() * product.getQuantity();
        }

        DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");
        tvGrandTotal.setText(formatter.format(total));
    }
}
