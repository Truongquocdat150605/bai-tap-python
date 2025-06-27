package com.example.truongquocdat_2123110209;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import com.example.truongquocdat_2123110209.BannerAdapter;
public class HomeActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView, productRecyclerView;
    private ViewPager2 bannerViewPager;
    private TabLayout bannerIndicator;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable autoSlideRunnable;
    private TextView cartItemCountTextView; // TextView để hiển thị số lượng sản phẩm trong giỏ hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Khởi tạo các thành phần
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        productRecyclerView = findViewById(R.id.productRecyclerView);
        bannerViewPager = findViewById(R.id.bannerViewPager);
        bannerIndicator = findViewById(R.id.bannerIndicator);
        ImageButton btnGoToCart = findViewById(R.id.btnGoToCart); // Tham chiếu đến ImageButton
        cartItemCountTextView = findViewById(R.id.cartItemCount); // TextView hiển thị số lượng

        // Thiết lập RecyclerView cho danh mục
        categoryRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Áo", R.drawable.ao_so_mi));
        categories.add(new Category("Quần", R.drawable.quan_jean));
        categories.add(new Category("Váy", R.drawable.vay));
        categories.add(new Category("Giày", R.drawable.giay_the_thao));
//        categories.add(new Category("Phụ kiện", R.drawable.ic_category_phu_kien));
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories);
        categoryRecyclerView.setAdapter(categoryAdapter);

        // Thiết lập RecyclerView cho sản phẩm (sản phẩm nổi bật)
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        List<Product> products = new ArrayList<>();
        products.add(new Product("Áo thun nam", R.drawable.product_ao_thun_nam, "150000", "Áo thun cotton mềm mại", "Nam", "https://example.com/ao_thun_nam.jpg"));
        products.add(new Product("Quần jean nữ", R.drawable.product_quan_jean_nu, "300000", "Quần jean ống rộng thời trang", "Nữ", "https://example.com/quan_jean_nu.jpg"));
        products.add(new Product("Váy liền thân", R.drawable.product_vay_lien_than, "450000", "Váy công sở thanh lịch", "Nữ", "https://example.com/vay_lien_than.jpg"));
        products.add(new Product("Giày sneakers", R.drawable.product_giay_sneakers, "700000", "Giày thể thao năng động", "Unisex", "https://example.com/giay_sneakers.jpg"));
        products.add(new Product("Nón lưỡi trai", R.drawable.product_non_luoi_trai, "80000", "Nón thời trang cá tính", "Unisex", "https://example.com/non_luoi_trai.jpg"));
        products.add(new Product("Túi xách nữ", R.drawable.product_tui_xach_nu, "500000", "Túi xách da cao cấp", "Nữ", "https://example.com/tui_xach_nu.jpg"));
     ProductAdapter productAdapter = new ProductAdapter(products);
        productRecyclerView.setAdapter(productAdapter);

        // Thiết lập ViewPager2 cho Banner
        List<Integer> bannerImages = new ArrayList<>();
        bannerImages.add(R.drawable.drawable_banner1);
        bannerImages.add(R.drawable.drawable_banner2);
        bannerImages.add(R.drawable.drawable_banner3);

        BannerAdapter bannerAdapter = new BannerAdapter(bannerImages);
        bannerViewPager.setAdapter(bannerAdapter);

        // Kết nối TabLayout với ViewPager2
        new TabLayoutMediator(bannerIndicator, bannerViewPager,
                (tab, position) -> {} // Không cần text cho tab indicator
        ).attach();

        // Tự động chuyển banner
        autoSlideRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = bannerViewPager.getCurrentItem();
                int totalItems = bannerAdapter.getItemCount();
                int nextItem = (currentItem + 1) % totalItems;
                bannerViewPager.setCurrentItem(nextItem, true);
                handler.postDelayed(this, 3000); // Chuyển sau 3 giây
            }
        };

        // Bắt đầu tự động chuyển banner khi Activity khởi tạo
        handler.postDelayed(autoSlideRunnable, 3000);

        // Xử lý sự kiện khi nhấn nút Giỏ hàng
        btnGoToCart.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật số lượng sản phẩm trong giỏ hàng khi Activity hiển thị lại
        updateCartItemCount();
        handler.postDelayed(autoSlideRunnable, 3000); // Khởi động lại tự động chuyển banner
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(autoSlideRunnable); // Dừng tự động chuyển banner khi Activity bị tạm dừng
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(autoSlideRunnable); // Xóa callback khi activity bị hủy
    }

    // Phương thức để cập nhật số lượng sản phẩm trong giỏ hàng
    private void updateCartItemCount() {
        int count = CartManager.getInstance().getTotalItemCount();
        if (count > 0) {
            cartItemCountTextView.setText(String.valueOf(count));
            cartItemCountTextView.setVisibility(View.VISIBLE); // Hiển thị số lượng
        } else {
            cartItemCountTextView.setVisibility(View.GONE); // Ẩn nếu giỏ hàng trống
        }
    }
}