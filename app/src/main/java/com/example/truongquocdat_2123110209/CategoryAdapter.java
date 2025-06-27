package com.example.truongquocdat_2123110209;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.content.Intent;
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<Category> categoryList;

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.categoryImage);
            textView = view.findViewById(R.id.categoryText);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.textView.setText(category.getName());
        holder.imageView.setImageResource(category.getImageResId());

        // Thêm sự kiện nhấp vào danh mục
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ProductListActivity.class);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}

// Class Category để lưu trữ dữ liệu
class Category {
    private String name;
    private int imageResId;

    public Category(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
}