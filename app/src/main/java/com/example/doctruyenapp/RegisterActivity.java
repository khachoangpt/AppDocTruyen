package com.example.doctruyenapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctruyenapp.database.Database;
import com.example.doctruyenapp.model.Account;

public class RegisterActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword, edtEmail;
    Button btnReturnLogin, btnRegister;
    Database database = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                if(username.equals("")||password.equals("")||email.equals("")){
                    Toast.makeText(RegisterActivity.this, "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    int role = 1;
                    Account a = new Account(username, password, email, role);
                    database.addAccount(a);
                    Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                }
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
