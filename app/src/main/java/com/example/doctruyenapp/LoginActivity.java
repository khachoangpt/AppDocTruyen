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
    //  AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //   db = AppDatabase.getInstance(this);

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

                //    ApiService.apiService.login(new Account(username, password));

                doLogin(new Account(username, password));

                //Using cursor to get data
                //Call getAllAccount() to get all accounts
                //  List<Account> accountList = db.accountDAO().getAllAccount();

                //   boolean check = false;

//                for (Account account : accountList) {
//                    String db_username = account.username;
//                    String db_password = account.password;
//
//                    //Account exist
//                    if(db_username.equals(username) && db_password.equals(password)){
//                        String role = account.role;
//                        int id = account.accountId;
//                        String email = account.email;
//                        String accountName = account.username;
//
//                        //Move to MainActivity
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//
//                        //Send data to MainActivity
//                        intent.putExtra("phanquyen", role);
//                        intent.putExtra("id", id);
//                        intent.putExtra("email",email);
//                        intent.putExtra("tentk", accountName);
//                        startActivity(intent);
//                        check = true;
//                        break;
//                    }
//                }
//                if(!check){
//                    Toast.makeText(LoginActivity.this, "Sai tên tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
//                }
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

                    // JSONObject jObjError = new JSONObject(response.errorBody().string());
                    // System.out.println("error: " + jObjError.toString())
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Server is busy! Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        edtUsername = findViewById(R.id.login_username);
        edtPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_btnLogin);
        btnRegister = findViewById(R.id.login_btnRegister);
        if(PreferrenceUtils.getJwt(this) != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


}
