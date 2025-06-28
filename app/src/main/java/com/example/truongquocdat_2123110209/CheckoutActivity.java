package com.example.truongquocdat_2123110209;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import android.widget.ImageView;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView rvCheckoutProducts;
    private CheckoutProductAdapter adapter;
    private List<CheckoutProduct> productList;
    private TextView tvGrandTotal;
    private Button btnPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        rvCheckoutProducts = findViewById(R.id.rvCheckoutProducts);
        tvGrandTotal = findViewById(R.id.tvGrandTotal);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        Intent intent = getIntent();
        productList = (ArrayList<CheckoutProduct>) intent.getSerializableExtra("checkout_list");
        if (productList == null) productList = new ArrayList<>();

        adapter = new CheckoutProductAdapter(this, productList);
        rvCheckoutProducts.setLayoutManager(new LinearLayoutManager(this));
        rvCheckoutProducts.setAdapter(adapter);

        updateTotalPrice();

        btnPlaceOrder.setOnClickListener(v -> showPaymentMethodDialog());
    }

    private void updateTotalPrice() {
        double total = 0;
        for (CheckoutProduct product : productList) {
            total += product.getPrice() * product.getQuantity();
        }
        DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");
        tvGrandTotal.setText(formatter.format(total));
    }

    private void showPaymentMethodDialog() {
        String[] paymentMethods = {
                "Thanh toán khi nhận hàng",
                "Momo",
                "Thẻ tín dụng (giả lập)",
                "Chuyển khoản ngân hàng (giả lập)"
        };

        new AlertDialog.Builder(this)
                .setTitle("Chọn phương thức thanh toán")
                .setItems(paymentMethods, (dialog, which) -> {
                    String selectedMethod = paymentMethods[which];
                    handlePayment(selectedMethod);
                })
                .show();
    }

    private void handlePayment(String method) {
        switch (method) {
            case "Thanh toán khi nhận hàng":
                Toast.makeText(this, "Bạn đã chọn thanh toán khi nhận hàng.", Toast.LENGTH_SHORT).show();
                break;
            case "Momo":
                showMomoQrDialog(); // ✅ thay vì chỉ Toast hoặc nhập số
                break;

            case "Thẻ tín dụng (giả lập)":
                Toast.makeText(this, "Thanh toán bằng thẻ tín dụng (giả lập).", Toast.LENGTH_SHORT).show();
                break;
            case "Chuyển khoản ngân hàng (giả lập)":
                Toast.makeText(this, "Vui lòng chuyển khoản đến số 123456789 - Ngân hàng XYZ.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    // ✅ Popup nhập số điện thoại Momo
    private void showMomoDialog() {
        EditText input = new EditText(this);
        input.setHint("Nhập số điện thoại Momo");
        input.setInputType(InputType.TYPE_CLASS_PHONE);

        new AlertDialog.Builder(this)
                .setTitle("Thanh toán Momo")
                .setMessage("Vui lòng nhập số điện thoại liên kết ví Momo:")
                .setView(input)
                .setPositiveButton("Xác nhận", (dialog, which) -> {
                    String phone = input.getText().toString().trim();
                    if (phone.matches("^0\\d{9}$")) {
                        Toast.makeText(this, "Đã thanh toán qua Momo số: " + phone, Toast.LENGTH_LONG).show();
                        // TODO: xử lý lưu đơn hàng tại đây nếu muốn
                    } else {
                        Toast.makeText(this, "Số điện thoại không hợp lệ.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Huỷ", null)
                .show();
    }
    private void showMomoQrDialog() {
        ImageView qrImage = new ImageView(this);
        qrImage.setImageResource(R.drawable.qr_momo_sample); // ảnh QR bạn thêm trong drawable
        int padding = getResources().getDimensionPixelSize(R.dimen.qr_padding); // ✅

        qrImage.setPadding(padding, padding, padding, padding);
        qrImage.setAdjustViewBounds(true);

        new AlertDialog.Builder(this)
                .setTitle("Quét mã QR để thanh toán")
                .setMessage("Sử dụng app Momo để quét mã dưới đây:")
                .setView(qrImage)
                .setPositiveButton("Đã quét xong", (dialog, which) -> {
                    Toast.makeText(this, "Đã thanh toán bằng Momo. Cảm ơn bạn!", Toast.LENGTH_SHORT).show();
                    // TODO: xử lý xác nhận đơn hàng nếu cần
                })
                .setNegativeButton("Huỷ", null)
                .show();
    }

}
