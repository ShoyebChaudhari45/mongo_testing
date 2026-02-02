package com.example.mongo_testing;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // Declare UI components
    EditText etEmail, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Load the UI layout for this screen
        setContentView(R.layout.activity_login);

        // Bind UI elements to Java variables
        etEmail = findViewById(R.id.etEmail);       // Email input field
        etPassword = findViewById(R.id.etPassword); // Password input field
        btnLogin = findViewById(R.id.btnLogin);     // Login button

        // Set click listener on Login button
        btnLogin.setOnClickListener(v -> login());
    }

    // -------------------------------------------------------
    // Method: login()
    // Purpose: Sends login credentials to API using Retrofit
    // -------------------------------------------------------
    private void login() {

        // Create a User object using entered email + password
        User user = new User(
                etEmail.getText().toString(),
                etPassword.getText().toString()
        );

        // Get API service instance from Retrofit client
        ApiService api = RetrofitClient.getApiService();

        // Call login endpoint asynchronously
        api.login(user).enqueue(new Callback<ApiResponse>() {

            // This executes when a response is received from the server
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                // Check if HTTP status is 200–299
                if (response.isSuccessful()) {

                    // Get message returned by API
                    Toast.makeText(LoginActivity.this,
                            response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    // Response received but not successful (e.g., 400/401/500)
                    Toast.makeText(LoginActivity.this,
                            "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            // This only executes when the request fails due to network issue or crash
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
