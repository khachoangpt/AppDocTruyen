package com.example.doctruyenapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctruyenapp.database.Database;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin, btnRegister;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mapping();

        database = new Database(this);

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

                //Using cursor to get data
                //Call getAllAccount() to get all accounts
                Cursor cursor = database.getAllAccount();

                boolean check = false;

                while (cursor.moveToNext()){
                    String db_username = cursor.getString(1);
                    String db_password = cursor.getString(2);

                    //Account exist
                    if(db_username.equals(username) && db_password.equals(password)){
                        int role = cursor.getInt(4);
                        int id = cursor.getInt(0);
                        String email = cursor.getString(3);
                        String accountName = cursor.getString(1);

                        //Move to MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                        //Send data to MainActivity
                        intent.putExtra("phanquyen", role);
                        intent.putExtra("id", id);
                        intent.putExtra("email",email);
                        intent.putExtra("tentk", accountName);
                        startActivity(intent);
                        check = true;
                        break;
                    }
                }
                if(!check){
                    Toast.makeText(LoginActivity.this, "Sai tên tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
                cursor.moveToFirst();
                cursor.close();
            }
        });
    }

    private void mapping() {
        edtUsername = findViewById(R.id.login_username);
        edtPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_btnLogin);
        btnRegister = findViewById(R.id.login_btnRegister);
    }
}
