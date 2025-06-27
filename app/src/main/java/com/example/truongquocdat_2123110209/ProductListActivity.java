package com.example.truongquocdat_2123110209;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private SearchView searchView;
    private Spinner filterCategory, filterGender, filterPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Khởi tạo các thành phần
        productRecyclerView = findViewById(R.id.productRecyclerView);
        searchView = findViewById(R.id.searchView);
        filterCategory = findViewById(R.id.filterCategory);
        filterGender = findViewById(R.id.filterGender);
        filterPrice = findViewById(R.id.filterPrice);
        Button buttonCancel = findViewById(R.id.buttonCancel); // Khai báo buttonCancel

        // Thiết lập RecyclerView
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productList = new ArrayList<>();
        loadSampleData();
        productAdapter = new ProductAdapter(productList);
        productRecyclerView.setAdapter(productAdapter);

        // Thiết lập Adapter cho Spinner
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterCategory.setAdapter(categoryAdapter);

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.genders, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterGender.setAdapter(genderAdapter);

        ArrayAdapter<CharSequence> priceAdapter = ArrayAdapter.createFromResource(this,
                R.array.prices, android.R.layout.simple_spinner_item);
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterPrice.setAdapter(priceAdapter);

        // Xử lý tìm kiếm
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterProducts(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProducts(newText);
                return true;
            }
        });

        // Xử lý bộ lọc
        filterCategory.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                filterProducts();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        filterGender.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                filterProducts();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        filterPrice.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                filterProducts();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        // Xử lý sự kiện nhấp vào nút Hủy
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng ProductListActivity và quay lại HomeActivity
            }
        });
    }

    private void loadSampleData() {
        productList.add(new Product("Áo sơ mi", 500000, R.drawable.ao_so_mi, "Nam"));
        productList.add(new Product("Quần jean", 700000, R.drawable.quan_jean, "Nữ"));
        productList.add(new Product("Áo thun", 300000, R.drawable.ao_thun, "Nam"));
        productList.add(new Product("Váy", 800000, R.drawable.vay, "Nữ"));
        productList.add(new Product("Giày thể thao", 1000000, R.drawable.giay_the_thao, "Nam"));
    }

    private void filterProducts() {
        String category = filterCategory.getSelectedItem().toString();
        String gender = filterGender.getSelectedItem().toString();
        String priceRange = filterPrice.getSelectedItem().toString();
        List<Product> filteredList = new ArrayList<>();

        for (Product product : productList) {
            boolean matchesCategory = category.equals("Tất cả") || product.getCategory().equals(category);
            boolean matchesGender = gender.equals("Tất cả") || product.getGender().equals(gender);
            boolean matchesPrice = priceRange.equals("Tất cả") ||
                    (priceRange.equals("Dưới 500k") && product.getPrice() < 500000) ||
                    (priceRange.equals("500k - 1tr") && product.getPrice() >= 500000 && product.getPrice() <= 1000000) ||
                    (priceRange.equals("Trên 1tr") && product.getPrice() > 1000000);

            if (matchesCategory && matchesGender && matchesPrice) {
                filteredList.add(product);
            }
        }
        productAdapter.updateList(filteredList);
    }

    private void filterProducts(String query) {
        List<Product> filteredList = new ArrayList<>();
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(product);
            }
        }
        productAdapter.updateList(filteredList);
    }

    // Lớp Product để lưu trữ dữ liệu sản phẩm
    class Product {
        private String name;
        private int price;
        private int imageResId;
        private String gender;

        public Product(String name, int price, int imageResId, String gender) {
            this.name = name;
            this.price = price;
            this.imageResId = imageResId;
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public int getImageResId() {
            return imageResId;
        }

        public String getGender() {
            return gender;
        }

        public String getCategory() {
            if (name.contains("Áo")) return "Áo";
            if (name.contains("Quần")) return "Quần";
            if (name.contains("Giày")) return "Giày";
            if (name.contains("Váy")) return "Áo";
            return "Tất cả";
        }
    }

    // Lớp ProductAdapter cho danh sách sản phẩm
    class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
        private List<Product> productList;

        public ProductAdapter(List<Product> productList) {
            this.productList = productList;
        }

        public void updateList(List<Product> newList) {
            productList = newList;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView productImage;
            TextView productName, productPrice;

            public ViewHolder(@NonNull View view) {
                super(view);
                productImage = view.findViewById(R.id.productImage);
                productName = view.findViewById(R.id.productName);
                productPrice = view.findViewById(R.id.productPrice);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_product, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Product product = productList.get(position);
            holder.productName.setText(product.getName());
            holder.productPrice.setText(String.format("%,d VNĐ", product.getPrice()));
            holder.productImage.setImageResource(product.getImageResId());

            // Thêm sự kiện nhấp vào sản phẩm
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), ProductDetailActivity.class);
                intent.putExtra("product_name", product.getName());
                intent.putExtra("product_price", product.getPrice());
                intent.putExtra("product_image", product.getImageResId());
                intent.putExtra("product_gender", product.getGender());
                holder.itemView.getContext().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }
    }
}