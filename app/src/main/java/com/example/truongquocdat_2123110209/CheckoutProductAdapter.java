package com.example.truongquocdat_2123110209;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CheckoutProductAdapter extends RecyclerView.Adapter<CheckoutProductAdapter.ViewHolder> {

    private Context context;
    private List<CheckoutProduct> productList;

    public CheckoutProductAdapter(Context context, List<CheckoutProduct> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public CheckoutProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_checkout_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutProductAdapter.ViewHolder holder, int position) {
        CheckoutProduct product = productList.get(position);
        holder.textName.setText(product.getName());
        holder.textPrice.setText(String.format("Giá: %,.0f VNĐ", product.getPrice()));
        holder.textQuantity.setText("Số lượng: " + product.getQuantity());

        Glide.with(context)
                .load(product.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView textName, textPrice, textQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageViewProduct);
            textName = itemView.findViewById(R.id.textViewProductName);
            textPrice = itemView.findViewById(R.id.textViewProductPrice);
            textQuantity = itemView.findViewById(R.id.textViewProductQuantity);
        }
    }
}
