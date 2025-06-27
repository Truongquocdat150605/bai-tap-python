package com.example.truongquocdat_2123110209;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        Button buttonCancel = findViewById(R.id.buttonCancel); // Tham chiếu đến nút Hủy

        buttonRegister.setOnClickListener(view -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegisterActivity.this, "Đăng ký thành công! Vui lòng đăng nhập.", Toast.LENGTH_LONG).show();
                // Sau khi đăng ký thành công, bạn muốn quay lại màn hình đăng nhập
                // Không cần tạo Intent mới nếu LoginActivity đã ở dưới cùng ngăn xếp
                finish(); // Đóng RegisterActivity, tự động quay lại LoginActivity
            }
        });

        // Xử lý sự kiện khi nhấn nút Hủy
        buttonCancel.setOnClickListener(view -> {
            finish(); // Đóng RegisterActivity và quay lại Activity trước đó (LoginActivity)
        });
    }
}