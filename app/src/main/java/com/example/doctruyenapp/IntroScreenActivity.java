package com.example.doctruyenapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctruyenapp.api.ApiService;
import com.example.doctruyenapp.utils.PreferrenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntroScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkToken();
            }
        }, 3000);
    }

    private void checkToken() {
        if (PreferrenceUtils.getJwt(getApplicationContext()) != null && !PreferrenceUtils.getJwt(getApplicationContext()).isEmpty()) {
            ApiService.apiService.isTokenValidation(PreferrenceUtils.getJwt(getApplicationContext())).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.code() == 401) {
                        Intent intent = new Intent(IntroScreenActivity.this, LoginActivity.class);
                        intent.putExtra("session", "expired");
                        startActivity(intent);
                        finish();
                    } else if (response.body()) {
                        Intent intent = new Intent(IntroScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(IntroScreenActivity.this, "Server is busy! Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Intent intent = new Intent(IntroScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}