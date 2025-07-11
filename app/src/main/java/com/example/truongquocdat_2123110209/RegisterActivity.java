package com.example.truongquocdat_2123110209;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://6868d42dd5933161d70c8ef5.mockapi.io/users"; // đổi thành endpoint của bạn
    private static final int MIN_PASSWORD_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        Button buttonCancel = findViewById(R.id.buttonCancel);

        buttonRegister.setOnClickListener(view -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showToast("Vui lòng nhập đầy đủ thông tin");
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showToast("Email không hợp lệ");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showToast("Mật khẩu không khớp");
                return;
            }

            if (password.length() < MIN_PASSWORD_LENGTH) {
                showToast("Mật khẩu phải ≥ " + MIN_PASSWORD_LENGTH + " ký tự");
                return;
            }

            registerUser(email, password);
        });

        buttonCancel.setOnClickListener(view -> finish());
    }

    private void registerUser(String email, String password) {
        JSONObject newUser = new JSONObject();
        try {
            newUser.put("email", email);
            newUser.put("password", password);
            newUser.put("name", "Người dùng mới"); // hoặc cho người dùng nhập tên nếu muốn
        } catch (JSONException e) {
            e.printStackTrace();
            showToast("Lỗi tạo dữ liệu đăng ký.");
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL,
                newUser,
                response -> {
                    showToast("Đăng ký thành công! Vui lòng đăng nhập.");
                    finish(); // quay lại LoginActivity
                },
                error -> {
                    error.printStackTrace();
                    showToast("Đăng ký thất bại. Kiểm tra kết nối mạng.");
                }
        );

        queue.add(request);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
