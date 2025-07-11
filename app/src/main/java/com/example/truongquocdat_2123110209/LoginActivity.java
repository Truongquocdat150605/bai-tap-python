package com.example.truongquocdat_2123110209;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String BASE_URL = "https://6868d42dd5933161d70c8ef5.mockapi.io/users";

    private static final int MIN_PASSWORD_LENGTH = 6;

    private Toast currentToast = null; // Để chống spam Toast

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        TextView buttonRegister = findViewById(R.id.buttonRegister);
        ImageView buttonGoogle = findViewById(R.id.buttonGoogle);
        ImageView buttonFacebook = findViewById(R.id.buttonFacebook);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

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
                editTextPassword.setError("Mật khẩu phải ≥ " + MIN_PASSWORD_LENGTH + " ký tự");
                isValid = false;
            }

            if (isValid) {
                loginUser(email, password, prefs);
            }
        });

        buttonRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        buttonGoogle.setOnClickListener(v ->
                showToast("Đăng nhập bằng Google (demo)"));

        buttonFacebook.setOnClickListener(v ->
                showToast("Đăng nhập bằng Facebook (demo)"));
    }

    private void loginUser(String email, String password, SharedPreferences prefs) {
        String url = BASE_URL + "?email=" + email + "&password=" + password;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    if (response.length() > 0) {
                        try {
                            JSONObject user = response.getJSONObject(0);
                            String name = user.optString("name", "Người dùng");

                            prefs.edit()
                                    .putBoolean("isLoggedIn", true)
                                    .putString("user_name", name)
                                    .putString("user_email", email)
                                    .apply();

                            showToast("Xin chào " + name + "!");
                            startActivity(new Intent(this, HomeActivity.class));
                            finish();
                        } catch (JSONException e) {
                            Log.e("LoginError", "Lỗi xử lý JSON", e);
                            showToast("Lỗi xử lý dữ liệu người dùng.");
                        }
                    } else {
                        showToast("Email hoặc mật khẩu không đúng.");
                    }
                },
                error -> {
                    Log.e("VolleyError", "Lỗi kết nối máy chủ", error);
                    showToast("Không thể kết nối máy chủ.");
                }
        );

        queue.add(request);
    }

    private void showToast(String message) {
        if (currentToast != null) currentToast.cancel();
        currentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        currentToast.show();
    }
}
