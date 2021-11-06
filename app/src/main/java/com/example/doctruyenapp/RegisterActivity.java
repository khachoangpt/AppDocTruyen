package com.example.doctruyenapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctruyenapp.api.ApiService;
import com.example.doctruyenapp.model.Account;
import com.example.doctruyenapp.model.ErrorException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword, edtEmail;
    Button btnReturnLogin, btnRegister;
    //  AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //   db = AppDatabase.getInstance(this);

        mapping();

        btnReturnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String email = edtEmail.getText().toString();
                if (username.equals("") || password.equals("") || email.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Please fill all the feild", Toast.LENGTH_SHORT).show();
                } else {
                    Account a = new Account(username, password, email, "USER");
                    doSignUp(a);

                }
            }
        });
    }


    private void doSignUp(Account account) {
        ApiService.apiService.signUp(account).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Register Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    ErrorException errorException = null;
                    try {
                        errorException = objectMapper.readValue(response.errorBody().string(), ErrorException.class);
                        System.out.println(errorException.toString());
                        Toast.makeText(RegisterActivity.this, errorException.getErrors().get(0), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Server is busy! Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mapping() {
        edtUsername = findViewById(R.id.register_username);
        edtPassword = findViewById(R.id.register_password);
        edtEmail = findViewById(R.id.register_email);
        btnReturnLogin = findViewById(R.id.register_btnReturnLogin);
        btnRegister = findViewById(R.id.register_btnRegister);
    }
}
