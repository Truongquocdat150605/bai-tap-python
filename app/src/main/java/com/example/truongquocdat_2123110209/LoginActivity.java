package com.example.truongquocdat_2123110209;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "MyPrefs";
    private static final String DEFAULT_EMAIL = "admin@example.com";
    private static final String DEFAULT_PASSWORD = "password123";
    private static final int MIN_PASSWORD_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editTextEmail      = findViewById(R.id.editTextEmail);
        EditText editTextPassword   = findViewById(R.id.editTextPassword);
        Button buttonLogin          = findViewById(R.id.buttonLogin);
        TextView buttonRegister     = findViewById(R.id.buttonRegister);
        ImageView buttonGoogle      = findViewById(R.id.buttonGoogle);
        ImageView buttonFacebook    = findViewById(R.id.buttonFacebook);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        buttonLogin.setOnClickListener(v -> {
            // reset lỗi
            editTextEmail.setError(null);
            editTextPassword.setError(null);

            String email    = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            boolean isValid = true;

            // validate email
            if (email.isEmpty()) {
                editTextEmail.setError("Vui lòng nhập Email");
                isValid = false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("Định dạng Email không hợp lệ");
                isValid = false;
            }

            // validate password
            if (password.isEmpty()) {
                editTextPassword.setError("Vui lòng nhập Mật khẩu");
                isValid = false;
            } else if (password.length() < MIN_PASSWORD_LENGTH) {
                editTextPassword.setError("Mật khẩu phải ≥ " + MIN_PASSWORD_LENGTH + " ký tự");
                isValid = false;
            }

            if (!isValid) return;

            // đọc thông tin hợp lệ (lần đầu fallback về admin mặc định)
            String validEmail    = prefs.getString("saved_email", DEFAULT_EMAIL);
            String validPassword = prefs.getString("saved_password", DEFAULT_PASSWORD);

            if (email.equals(validEmail) && password.equals(validPassword)) {
                // đánh dấu đã login
                prefs.edit().putBoolean("isLoggedIn", true).apply();

                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                // đi HomeActivity
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Email hoặc Mật khẩu không đúng.", Toast.LENGTH_LONG).show();
            }
        });

        buttonRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );

        buttonGoogle.setOnClickListener(v ->
                Toast.makeText(this, "Đăng nhập bằng Google (demo)", Toast.LENGTH_SHORT).show()
        );

        buttonFacebook.setOnClickListener(v ->
                Toast.makeText(this, "Đăng nhập bằng Facebook (demo)", Toast.LENGTH_SHORT).show()
        );
    }
}
