package com.example.doctruyenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {
    TextView txtInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        txtInformation = findViewById(R.id.tv_information);
        String info = "App được phát triển bởi team 4 Class: SE1417\nPRM391 Project\nFall2021\nPhiên bản: v1.0";
        txtInformation.setText(info);
    }
}