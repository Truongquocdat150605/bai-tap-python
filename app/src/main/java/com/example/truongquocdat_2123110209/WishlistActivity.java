package com.example.truongquocdat_2123110209;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;
import java.util.List;
import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {

    private RecyclerView wishlistRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        wishlistRecyclerView = findViewById(R.id.wishlistRecyclerView);
        wishlistRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Danh sách sản phẩm yêu thích (giả lập)
//        List<Product> wishlistProducts = new ArrayList<>();
//        wishlistProducts.add(new Product("Áo thun nam", R.drawable.product_ao_thun_nam, "150000", "Áo thun cotton mềm mại", "Nam", "https://example.com/ao_thun_nam.jpg"));
        // Bạn thêm các sản phẩm khác tương tự ở đây (hoặc sau này lấy từ WishlistManager)
        List<Product> wishlistProducts = WishlistManager.getInstance(this).getList();


        ProductAdapter adapter = new ProductAdapter(wishlistProducts,true);
        wishlistRecyclerView.setAdapter(adapter);
    }
}
