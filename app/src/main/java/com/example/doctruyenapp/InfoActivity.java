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
        String info = "Ứng dụng được làm bởi team 4 Class: SE1417\nPRM391 Project\nFall2021";
        txtInformation.setText(info);
    }
}