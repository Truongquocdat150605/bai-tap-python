package com.example.truongquocdat_2123110209;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hiển thị thông báo để kiểm tra có vào được MainActivity không
        Toast.makeText(this, "Đang ở MainActivity", Toast.LENGTH_SHORT).show();

        // Tham chiếu đến nút Đăng xuất
        Button buttonLogout = findViewById(R.id.buttonLogout);

        // Thiết lập sự kiện khi nhấn nút Đăng xuất
        buttonLogout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);

            // Xóa ngăn xếp activity cũ để tránh quay lại
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent); // Mở lại màn hình đăng nhập
            finish();              // Đóng MainActivity
        });
    }
}
