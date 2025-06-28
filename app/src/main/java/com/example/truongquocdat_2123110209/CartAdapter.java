package com.example.truongquocdat_2123110209;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private OnCartItemChangeListener listener;

    public interface OnCartItemChangeListener {
        void onQuantityChanged();
        void onItemRemoved();
    }

    public CartAdapter(List<CartItem> cartItems, OnCartItemChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.cartProductImage.setImageResource(item.getProduct().getImageResId());
        holder.cartProductName.setText(item.getProduct().getName());

        // Định dạng giá tiền
        DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");
        String formattedPrice = formatter.format(Double.parseDouble(item.getProduct().getPrice()));
        holder.cartProductPrice.setText(formattedPrice);

        holder.cartProductQuantity.setText(String.valueOf(item.getQuantity()));
        holder.cartTotalItemPrice.setText(formatter.format(item.getTotalPrice()));

        // Xử lý nút giảm số lượng
        holder.btnDecreaseQuantity.setOnClickListener(v -> {
            int currentQuantity = item.getQuantity();
            if (currentQuantity > 1) {
                item.setQuantity(currentQuantity - 1);
                CartManager.getInstance().updateProductQuantity(item.getProduct(), item.getQuantity());
                notifyItemChanged(position);
                if (listener != null) {
                    listener.onQuantityChanged();
                }
            } else {
                CartManager.getInstance().removeItem(item.getProduct());
                cartItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItems.size());
                if (listener != null) {
                    listener.onItemRemoved();
                }
            }
        });

        // Xử lý nút tăng số lượng
        holder.btnIncreaseQuantity.setOnClickListener(v -> {
            int currentQuantity = item.getQuantity();
            item.setQuantity(currentQuantity + 1);
            CartManager.getInstance().addProduct(item.getProduct(), 1);
            notifyItemChanged(position);
            if (listener != null) {
                listener.onQuantityChanged();
            }
        });

        // Xử lý nút xóa sản phẩm
        holder.btnRemoveItem.setOnClickListener(v -> {
            CartManager.getInstance().removeItem(item.getProduct());
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            if (listener != null) {
                listener.onItemRemoved();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartProductImage;
        TextView cartProductName, cartProductPrice, cartProductQuantity, cartTotalItemPrice;
        Button btnDecreaseQuantity, btnIncreaseQuantity;
        ImageButton btnRemoveItem;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartProductImage = itemView.findViewById(R.id.cartProductImage);
            cartProductName = itemView.findViewById(R.id.cartProductName);
            cartProductPrice = itemView.findViewById(R.id.cartProductPrice);
            cartProductQuantity = itemView.findViewById(R.id.cartProductQuantity);
            cartTotalItemPrice = itemView.findViewById(R.id.cartTotalItemPrice);
            btnDecreaseQuantity = itemView.findViewById(R.id.btnDecreaseQuantity);
            btnIncreaseQuantity = itemView.findViewById(R.id.btnIncreaseQuantity);
            btnRemoveItem = itemView.findViewById(R.id.btnRemoveItem); // Đã sửa thành ImageButton
        }
    }

    public void updateCartItems(List<CartItem> newCartItems) {
        this.cartItems = newCartItems;
        notifyDataSetChanged();
    }
}
