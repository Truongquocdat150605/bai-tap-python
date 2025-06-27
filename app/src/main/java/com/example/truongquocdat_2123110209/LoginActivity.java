package com.example.truongquocdat_2123110209;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;  // ✅ Thêm dòng này vì m dùng TextView
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String VALID_EMAIL = "admin@example.com";
    private static final String VALID_PASSWORD = "password123";
    private static final int MIN_PASSWORD_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        TextView buttonRegister = findViewById(R.id.buttonRegister); // Đúng kiểu TextView

        ImageView buttonGoogle = findViewById(R.id.buttonGoogle);
        ImageView buttonFacebook = findViewById(R.id.buttonFacebook);

        buttonLogin.setOnClickListener(v -> {
            editTextEmail.setError(null);
            editTextPassword.setError(null);

            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            boolean isValid = true;

            if (email.isEmpty()) {
                editTextEmail.setError("Vui lòng nhập Email");
                isValid = false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("Định dạng Email không hợp lệ");
                isValid = false;
            }

            if (password.isEmpty()) {
                editTextPassword.setError("Vui lòng nhập Mật khẩu");
                isValid = false;
            } else if (password.length() < MIN_PASSWORD_LENGTH) {
                editTextPassword.setError("Mật khẩu phải có ít nhất " + MIN_PASSWORD_LENGTH + " ký tự");
                isValid = false;
            }

            if (isValid) {
                if (email.equals(VALID_EMAIL) && password.equals(VALID_PASSWORD)) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Email hoặc Mật khẩu không đúng.", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        buttonGoogle.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Đăng nhập bằng Google (demo)", Toast.LENGTH_SHORT).show();
            // TODO: Tích hợp Google Sign-In nếu muốn
        });

        buttonFacebook.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Đăng nhập bằng Facebook (demo)", Toast.LENGTH_SHORT).show();
            // TODO: Tích hợp Facebook Login nếu muốn
        });
    }
}
