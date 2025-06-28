package com.example.truongquocdat_2123110209;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;
    private boolean isWishlistMode;

    public ProductAdapter(List<Product> productList, boolean isWishlistMode) {
        this.productList = productList;
        this.isWishlistMode = isWishlistMode;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productDescription;
        ImageView favoriteButton;

        public ViewHolder(@NonNull View view) {
            super(view);
            productImage = view.findViewById(R.id.productImage);
            productName = view.findViewById(R.id.productName);
            productPrice = view.findViewById(R.id.productPrice);
            productDescription = view.findViewById(R.id.productDescription);
            favoriteButton = view.findViewById(R.id.favoriteButton);
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
        Context context = holder.itemView.getContext();
        WishlistManager wishlist = WishlistManager.getInstance(context);

        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice() + " VNĐ");
        holder.productDescription.setText("Mô tả: " + product.getDescription());

        if (product.getImageResId() != 0) {
            holder.productImage.setImageResource(product.getImageResId());
        } else {
            holder.productImage.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // Hiện đúng icon yêu thích
        holder.favoriteButton.setImageResource(
                wishlist.getList().contains(product) ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline
        );

        // Xử lý click vào trái tim
        holder.favoriteButton.setOnClickListener(v -> {
            if (wishlist.getList().contains(product)) {
                wishlist.remove(product);
                Toast.makeText(context, "Đã xoá khỏi yêu thích", Toast.LENGTH_SHORT).show();

                if (isWishlistMode) {
                    productList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                } else {
                    holder.favoriteButton.setImageResource(R.drawable.ic_heart_outline);
                }

            } else {
                wishlist.add(product);
                holder.favoriteButton.setImageResource(R.drawable.ic_heart_filled);
                Toast.makeText(context, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
            }
        });

        // Xem chi tiết sản phẩm
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("product_name", product.getName());
            intent.putExtra("product_price", product.getPrice());
            intent.putExtra("product_image", product.getImageResId());
            intent.putExtra("product_gender", product.getGender());
            intent.putExtra("product_description", product.getDescription());
            intent.putExtra("product_image_url", product.getImageUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateList(List<Product> newList) {
        productList = newList;
        notifyDataSetChanged();
    }
}
