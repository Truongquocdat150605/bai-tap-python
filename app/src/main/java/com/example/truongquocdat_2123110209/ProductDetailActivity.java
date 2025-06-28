package com.example.truongquocdat_2123110209;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailActivity extends AppCompatActivity {

    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Khởi tạo các thành phần
        ImageView detailImage = findViewById(R.id.detailImage);
        TextView detailName = findViewById(R.id.detailName);
        TextView detailPrice = findViewById(R.id.detailPrice);
        TextView detailGender = findViewById(R.id.detailGender);
        TextView detailDescription = findViewById(R.id.detailDescription);
        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        Button buttonCancel = findViewById(R.id.buttonCancel); // Khởi tạo nút Hủy

        // Lấy dữ liệu từ Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("product_name", "Không có tên");
            String price = extras.getString("product_price", "0");
            int imageResId = extras.getInt("product_image", R.drawable.ic_category_placeholder);
            String gender = extras.getString("product_gender", "Không xác định");
            String description = extras.getString("product_description", "");
            String imageUrl = extras.getString("product_image_url", "");

            // Tạo đối tượng Product đúng constructor 6 tham số
            currentProduct = new Product(name, imageResId, price, description, gender, imageUrl);

            // Hiển thị dữ liệu
            detailName.setText(currentProduct.getName());
            try {
                int priceInt = Integer.parseInt(currentProduct.getPrice().replaceAll("[^0-9]", ""));
                detailPrice.setText(String.format("%,d VNĐ", priceInt));
            } catch (Exception e) {
                detailPrice.setText(currentProduct.getPrice());
            }

            // Xử lý hình ảnh
            if (!imageUrl.isEmpty()) {
                // Sử dụng Glide để tải ảnh từ URL (cần thêm thư viện Glide)
                // Glide.with(this).load(imageUrl).into(detailImage);
                detailImage.setImageResource(imageResId); // Fallback nếu không tích hợp Glide
            } else {
                detailImage.setImageResource(currentProduct.getImageResId());
            }

            detailGender.setText("Giới tính: " + (currentProduct.getGender() != null ? currentProduct.getGender() : "Không xác định"));
            detailDescription.setText(currentProduct.getDescription() != null ? currentProduct.getDescription() : "Không có mô tả");
        } else {
            detailName.setText("Không có thông tin");
            detailPrice.setText("0 VNĐ");
            detailImage.setImageResource(R.drawable.ic_category_placeholder);
            detailGender.setText("Giới tính: Không xác định");
            detailDescription.setText("Không có mô tả");
            currentProduct = null;
        }

        // Sự kiện nút "Thêm vào giỏ hàng"
        btnAddToCart.setOnClickListener(v -> {
            if (currentProduct != null) {
                CartManager.getInstance().addProduct(currentProduct, 1);
                Toast.makeText(this, "Đã thêm " + currentProduct.getName() + " vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Không thể thêm sản phẩm trống vào giỏ hàng.", Toast.LENGTH_SHORT).show();
            }
        });

        // Sự kiện nút "Hủy" để quay lại
        buttonCancel.setOnClickListener(v -> {
            finish(); // Đóng activity hiện tại và quay lại activity trước đó
        });
    }
}