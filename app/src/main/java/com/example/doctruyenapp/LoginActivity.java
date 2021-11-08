package com.example.doctruyenapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctruyenapp.api.ApiService;
import com.example.doctruyenapp.model.Account;
import com.example.doctruyenapp.model.ErrorException;
import com.example.doctruyenapp.utils.PreferrenceUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        //Click event for button register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get username and password
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if (username.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Vui lòng điền tất cả các trường", Toast.LENGTH_SHORT).show();
                } else {
                    doLogin(new Account(username, password));

                }
            }
        });
    }

    private void doLogin(Account account) {
        ApiService.apiService.login(account).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    PreferrenceUtils.saveJwt(response.body(), LoginActivity.this);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    ErrorException errorException = null;
                    try {
                        errorException = objectMapper.readValue(response.errorBody().string(), ErrorException.class);
                        System.out.println(errorException.toString());
                        Toast.makeText(LoginActivity.this, errorException.getErrors().get(0), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Hệ thống bận", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        edtUsername = findViewById(R.id.login_username);
        edtPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_btnLogin);
        btnRegister = findViewById(R.id.login_btnRegister);
        Intent intent = getIntent();
        if (intent.getStringExtra("session") != null) {
            showDialog();
        }
    }

    private void showDialog() {
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Thông báo")
                .setMessage("Phiên đã hết hạn. Vui lòng đăng nhập lại!")
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).show();
    }
}
