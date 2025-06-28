package com.example.truongquocdat_2123110209;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private SearchView searchView;
    private Spinner filterCategory, filterGender, filterPrice;
    private Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Ánh xạ view
        productRecyclerView = findViewById(R.id.productRecyclerView);
        searchView = findViewById(R.id.searchView);
        filterCategory = findViewById(R.id.filterCategory);
        filterGender = findViewById(R.id.filterGender);
        filterPrice = findViewById(R.id.filterPrice);
        buttonCancel = findViewById(R.id.buttonCancel);

        // Thiết lập RecyclerView
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productList = new ArrayList<>();
        loadSampleData();
        productAdapter = new ProductAdapter(productList,false);
        productRecyclerView.setAdapter(productAdapter);

        // Adapter cho Spinner
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

        // Nhận Intent từ Home
        String searchQuery = getIntent().getStringExtra("searchQuery");
        if (searchQuery != null && !searchQuery.isEmpty()) {
            searchView.setQuery(searchQuery, false); // Chỉ đặt nội dung, không auto submit
            filterProducts(searchQuery);             // Gọi hàm lọc đúng dữ liệu
        }


        // Nhận Intent từ category
        String selectedCategory = getIntent().getStringExtra("selectedCategory");
        if (selectedCategory != null) {
            int categoryPosition = categoryAdapter.getPosition(selectedCategory);
            if (categoryPosition >= 0) {
                filterCategory.setSelection(categoryPosition);
            }
            filterProducts();
        }

        // Lắng nghe tìm kiếm
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                filterProducts(query);
                return true;
            }

            @Override public boolean onQueryTextChange(String newText) {
                filterProducts(newText);
                return true;
            }
        });

        // Spinner bộ lọc
        filterCategory.setOnItemSelectedListener(simpleFilterListener);
        filterGender.setOnItemSelectedListener(simpleFilterListener);
        filterPrice.setOnItemSelectedListener(simpleFilterListener);

        // Nút Hủy
        buttonCancel.setOnClickListener(v -> finish());
    }

    private void loadSampleData() {
        productList.add(new Product("Áo sơ mi", R.drawable.ao_so_mi, "500000", "Áo sơ mi cao cấp", "Nam", ""));
        productList.add(new Product("Quần jean", R.drawable.quan_jean, "700000", "Quần jean bền đẹp", "Nữ", ""));
        productList.add(new Product("Áo thun", R.drawable.ao_thun, "300000", "Áo thun thoải mái", "Nam", ""));
        productList.add(new Product("Váy", R.drawable.vay, "800000", "Váy thời trang", "Nữ", ""));
        productList.add(new Product("Giày thể thao", R.drawable.giay_the_thao, "1000000", "Giày thể thao chất lượng", "Nam", ""));
    }


    private void filterProducts() {
        filterProducts(searchView.getQuery().toString()); // Gọi lại hàm chính với query hiện tại
    }

    private void filterProducts(String query) {
        String keyword = removeAccents(query);
        String category = removeAccents(filterCategory.getSelectedItem().toString());
        String gender = removeAccents(filterGender.getSelectedItem().toString());
        String priceRange = removeAccents(filterPrice.getSelectedItem().toString());

        List<Product> filteredList = new ArrayList<>();

        for (Product product : productList) {
            String name = removeAccents(product.getName());
            String productCategory = removeAccents(product.getCategory());
            String productGender = removeAccents(product.getGender());
            int productPrice = Integer.parseInt(product.getPrice());

            boolean matchKeyword = name.contains(keyword);
            boolean matchCategory = category.equals("tat ca") || productCategory.equals(category);
            boolean matchGender = gender.equals("tat ca") || productGender.equals(gender);
            boolean matchPrice = priceRange.equals("tat ca") ||
                    (priceRange.contains("duoi") && productPrice < 500000) ||
                    (priceRange.contains("500k") && productPrice >= 500000 && productPrice <= 1000000) ||
                    (priceRange.contains("tren") && productPrice > 1000000);

            if (matchKeyword && matchCategory && matchGender && matchPrice) {
                filteredList.add(product);
            }
        }

        productAdapter.updateList(filteredList);
    }


    // Bộ lọc dùng chung
    private final android.widget.AdapterView.OnItemSelectedListener simpleFilterListener =
            new android.widget.AdapterView.OnItemSelectedListener() {
                @Override public void onItemSelected(android.widget.AdapterView<?> parent, View view, int pos, long id) {
                    filterProducts();
                }
                @Override public void onNothingSelected(android.widget.AdapterView<?> parent) {}
            };

    // Xử lý bỏ dấu tiếng Việt
    public static String removeAccents(String text) {
        if (text == null) return "";
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
                .matcher(normalized)
                .replaceAll("")
                .toLowerCase()
                .trim();
    }
}
