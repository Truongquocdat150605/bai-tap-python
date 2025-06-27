package com.example.truongquocdat_2123110209;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView textProductName, textProductPrice, textProductDesc;

        public ViewHolder(@NonNull View view) {
            super(view);
            imageProduct = view.findViewById(R.id.productImage);
            textProductName = view.findViewById(R.id.productName);
            textProductPrice = view.findViewById(R.id.productPrice);
            textProductDesc = view.findViewById(R.id.productDesc);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.imageProduct.setImageResource(product.getImageResId());
        holder.textProductName.setText(product.getName());
        holder.textProductPrice.setText(product.getPrice());
        holder.textProductDesc.setText(product.getDescription());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
